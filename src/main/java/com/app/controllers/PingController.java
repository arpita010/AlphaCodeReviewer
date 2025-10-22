package com.app.controllers;

import com.app.services.OllamaCodeAnalyzerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/health")
@Slf4j
@RequiredArgsConstructor
public class PingController {

  private final OllamaCodeAnalyzerService codeAnalyzerService;

  @GetMapping("")
  public String get() {
    log.info("Service Ping Request Received at timestamp : {}", System.currentTimeMillis());
    return "Service is healthy";
  }

  @GetMapping("/model")
  public String modelInvoked() {
    codeAnalyzerService.call();
    return "Ollama Model invoked";
  }
}
