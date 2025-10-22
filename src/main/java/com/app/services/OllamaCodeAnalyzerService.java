package com.app.services;

import com.app.listeners.request.PullEditRequest;
import com.app.listeners.request.PullRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class OllamaCodeAnalyzerService implements CodeAnalyzerService {
  private final OllamaChatModel ollamaChatModel;
  private final ObjectMapper objectMapper;
  private final CodeDiffFetcherService codeDiffFetcherService;

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

  // TODO:
  // invoke model - title, body, diff.
  // fetch review comments -
  // Publish comment event
  // push comment using github.
}
