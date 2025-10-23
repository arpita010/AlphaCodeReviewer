package com.app.services;

import com.app.constants.ConverterType;
import com.app.constants.KafkaTopicName;
import com.app.data.CreateReviewCommentDto;
import com.app.data.CustomReviewResponse;
import com.app.factory.ConverterFactory;
import com.app.listeners.request.PullEditRequest;
import com.app.utils.Converter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OllamaCodeAnalyzerService implements CodeAnalyzerService {
  private final OllamaChatModel ollamaChatModel;
  private final ObjectMapper objectMapper;
  private final CodeDiffFetcherService codeDiffFetcherService;
  private final KafkaService kafkaService;
  private Converter<CreateReviewCommentDto> createReviewCommentDtoConverter;
  private Converter<List<CustomReviewResponse>> customReviewResponseConverter;
  private final ConverterFactory converterFactory;

  @PostConstruct
  public void initialize() {
    this.createReviewCommentDtoConverter =
        converterFactory.getConverter(ConverterType.CREATE_REVIEW_COMMENT_DTO_CONVERTER);
    this.customReviewResponseConverter =
        converterFactory.getConverter(ConverterType.CUSTOM_REVIEW_RESPONSE_CONVERTER);
  }

  public void call() {
    Prompt prompt = new Prompt("Tell me what is the temperature for " + "meerut " + "today");

    ChatResponse response = ollamaChatModel.call(prompt);

    log.info("Response from chat client : {}", response);
  }

  @Override
  public void analyze(String message) {
    try {
      PullEditRequest request = objectMapper.readValue(message, PullEditRequest.class);
      log.info("Converted Pull Request : {} ", request);
      invokeModel(request);
    } catch (Exception e) {
      log.error("Error occurred while analyzing code for request {} : {}", message, e.getMessage());
    }
  }

  private void invokeModel(PullEditRequest request) {
    String promptContent = createPromptContent(request);
    Prompt prompt = new Prompt(promptContent);
    ChatResponse response = ollamaChatModel.call(prompt);
    log.info("Response from chat client for code review : {}", response);
    printJson(response);
    CreateReviewCommentDto createReviewCommentDto = createReviewComment(request, response);
    publishCreateReviewCommentEvent(createReviewCommentDto);
  }

  private String readPromptFile() {
    try (InputStream inputStream =
        getClass()
            .getClassLoader()
            .getResourceAsStream("aiPrompts" + "/JavaCodeReviewPrompt" + ".txt")) {
      String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
      log.info("Generic Prompt file content : {}", content);
      return content;
    } catch (Exception e) {
      log.error("Error occurred while reading prompt file : {}", e.getMessage());
    }
    return null;
  }

  private String createPromptContent(PullEditRequest request) {
    String changes = codeDiffFetcherService.fetchDiff(request.getPullRequest().getDiffUrl());
    if (changes == null || changes.isEmpty()) {
      log.info("No differences found for pull request {}", request.getPullRequest().getId());
      return null;
    }
    String genericPrompt = readPromptFile();
    if (genericPrompt == null) {
      log.info("Unable to read generic prompt file...");
      throw new RuntimeException("Unable to read generic prompt file");
    }
    String aiPromptContent = genericPrompt + "\n\n" + changes;
    log.info("Complete AI Prompt content : {}", aiPromptContent);
    return aiPromptContent;
  }

  private CreateReviewCommentDto createReviewComment(
      PullEditRequest request, ChatResponse chatResponse) {
    String issueNumber = request.getPullRequest().getNumber();
    String fullName = request.getRepository().getFullName();
    String title = request.getPullRequest().getTitle();
    String body = request.getPullRequest().getBody();
    String content = extractResponseFromModelResponse(chatResponse);
    CreateReviewCommentDto createReviewCommentDto =
        CreateReviewCommentDto.builder()
            .issueNumber(issueNumber)
            .body(body)
            .fullName(fullName)
            .title(title)
            .modelResponse(content)
            .lastCommitSha(
                codeDiffFetcherService.getLastCommitSha(request.getPullRequest().getCommitsUrl()))
            .build();
    return createReviewCommentDto;
  }

  private String extractResponseFromModelResponse(ChatResponse chatResponse) {
    List<Generation> list = chatResponse.getResults();
    List<CustomReviewResponse> customReviewResponses = new ArrayList<>();

    for (Generation generation : list) {
      String textResponse = generation.getOutput().getText();
      if (textResponse == null || textResponse.isEmpty()) continue;
      CustomReviewResponse currentResponse = extractJsonFromTextResponse(textResponse);
      if (currentResponse != null) {
        customReviewResponses.add(currentResponse);
      }
    }
    String response = customReviewResponseConverter.serialize(customReviewResponses);
    return response;
  }

  private CustomReviewResponse extractJsonFromTextResponse(String text) {
    String modifiedText = text.replaceAll("\n", "");
    log.info("Modified text : {}", modifiedText);
    int startIndex = modifiedText.indexOf("```json") + "```json".length();
    int endIndex = modifiedText.lastIndexOf("```");
    String jsonResponse = modifiedText.substring(startIndex, endIndex).trim();
    log.info("JSON response after extraction : {}", jsonResponse);
    try {
      CustomReviewResponse response =
          objectMapper.readValue(jsonResponse, CustomReviewResponse.class);
      log.info("Custom Review Response : {}", response);
      return response;
    } catch (Exception e) {
      log.error("Error occurred while mapping custom review response : {}", e.getMessage());
    }
    return null;
  }

  private void publishCreateReviewCommentEvent(CreateReviewCommentDto createReviewCommentDto) {
    String message = createReviewCommentDtoConverter.serialize(createReviewCommentDto);
    kafkaService.publishEvent(KafkaTopicName.CREATE_REVIEW_COMMENT, message);
  }

  private void printJson(ChatResponse chatResponse) {
    try {
      String str = objectMapper.writeValueAsString(chatResponse);
      log.info("JSON string response : {}", str);
    } catch (Exception e) {
      log.error("Error occurred while printing chat response : {}", e.getMessage());
    }
  }
}
