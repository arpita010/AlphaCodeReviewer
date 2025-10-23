package com.app.consumers;

import com.app.constants.KafkaConstants;
import com.app.constants.ModelName;
import com.app.factory.CodeAnalyzerServiceFactory;
import com.app.services.CodeAnalyzerService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PullEventConsumer {
  private final CodeAnalyzerServiceFactory factory;
  private CodeAnalyzerService codeAnalyzerService;

  @PostConstruct
  public void initialize() {
    codeAnalyzerService = factory.getInstance(ModelName.OLLAMA);
  }

  @KafkaListener(topics = KafkaConstants.PULL_REQUEST, groupId = "group-id-1")
  public void consumePullEvent(String message, Acknowledgment acknowledgment) {
    log.info(
        "Consuming pull event message for topic {} : {}", KafkaConstants.PULL_REQUEST, message);
    // TODO: Configure AI tools to analyze the submitted code review.
    codeAnalyzerService.analyze(message);
    log.info("Consumed pull event message for topic {} : {}", KafkaConstants.PULL_REQUEST, message);
  }
}
