package com.app.consumers;

import com.app.constants.KafkaConstants;
import com.app.constants.ModelName;
import com.app.factory.CodeAnalyzerServiceFactory;
import com.app.services.CodeAnalyzerService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@Configuration
public class PullEventConsumer {
  @Autowired private CodeAnalyzerServiceFactory factory;
  private CodeAnalyzerService codeAnalyzerService;

  @PostConstruct
  public void initialize() {
    codeAnalyzerService = factory.getInstance(ModelName.OLLAMA);
  }

  @KafkaListener(topics = KafkaConstants.PULL_REQUEST)
  public void consumePullEvent(String message) {
    log.info(
        "Consuming pull event message for topic {} : {}", KafkaConstants.PULL_REQUEST, message);
    // TODO: Configure AI tools to analyze the submitted code review.
    codeAnalyzerService.analyze(message);
  }
}
