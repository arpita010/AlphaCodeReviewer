package com.app.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodeAnalyzerService {
  private final OllamaChatModel ollamaChatModel;

  public void call() {
    Prompt prompt = new Prompt("Tell me what is the temperature for " + "meerut " + "today");

    ChatResponse response = ollamaChatModel.call(prompt);

    log.info("Response from chat client : {}", response);
  }
}
