package com.app.services;

import com.app.listeners.request.PullRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OllamaCodeAnalyzerService implements CodeAnalyzerService {
  private final OllamaChatModel ollamaChatModel;
  private final ObjectMapper objectMapper;

  public void call() {
    Prompt prompt = new Prompt("Tell me what is the temperature for " + "meerut " + "today");

    ChatResponse response = ollamaChatModel.call(prompt);

    log.info("Response from chat client : {}", response);
  }

  @Override
  public void analyze(String message) {
    try {
      PullRequest request = objectMapper.convertValue(message, PullRequest.class);
      log.info("Converted Pull Request : {} ", request);
    } catch (Exception e) {
      log.error("Error occurred while analyzing code for request {} : {}", message, e.getMessage());
    }
  }
}
