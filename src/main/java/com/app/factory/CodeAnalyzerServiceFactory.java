package com.app.factory;

import com.app.constants.ModelName;
import com.app.services.CodeAnalyzerService;
import com.app.services.OllamaCodeAnalyzerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodeAnalyzerServiceFactory {
  private final OllamaCodeAnalyzerService ollamaCodeAnalyzerService;

  public CodeAnalyzerService getInstance(ModelName name) {
    switch (name) {
      case ModelName.OLLAMA -> {
        return ollamaCodeAnalyzerService;
      }
      default -> {
        throw new IllegalArgumentException("Invalid model name");
      }
    }
  }
}
