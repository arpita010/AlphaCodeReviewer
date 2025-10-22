package com.app.services;

import com.app.constants.KafkaTopicName;
import com.app.data.CreateReviewCommentDto;
import com.app.listeners.request.PullEditRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OllamaCodeAnalyzerService implements CodeAnalyzerService {
  private final OllamaChatModel ollamaChatModel;
  private final ObjectMapper objectMapper;
  private final CodeDiffFetcherService codeDiffFetcherService;
  private final KafkaService kafkaService;

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
            .build();
    return createReviewCommentDto;
  }

  private String extractResponseFromModelResponse(ChatResponse chatResponse) {
    List<Generation> list = chatResponse.getResults();
    String response = null;

  }

  private void publishCreateReviewCommentEvent(CreateReviewCommentDto createReviewCommentDto) {
    try {
      String message = objectMapper.writeValueAsString(createReviewCommentDto);
      kafkaService.publishEvent(KafkaTopicName.CREATE_REVIEW_COMMENT, message);
    } catch (Exception e) {
      log.error("Error occurred while publishing create review comment event : {}", e.getMessage());
    }
  }

  private void printJson(ChatResponse chatResponse) {
    try {
      String str = objectMapper.writeValueAsString(chatResponse);
      log.info("JSON string response : {}", str);
    } catch (Exception e) {
      log.error("Error occurred while printing chat response : {}", e.getMessage());
    }
  }

  // TODO:
  // invoke model - title, body, diff.
  // fetch review comments -
  // Publish comment event
  // push comment using github.
}
