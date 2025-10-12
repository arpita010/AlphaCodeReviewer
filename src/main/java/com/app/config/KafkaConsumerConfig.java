package com.app.config;

import com.app.constants.KafkaConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@Configuration
public class KafkaConsumerConfig {

  @KafkaListener(topics = "my-first-topic", groupId = "group-id")
  public void consumeMessage(String value) {
    log.info("Consumed message : {}", value);
  }

  @KafkaListener(topics = KafkaConstants.PR_EDIT_TOPIC_NAME)
  public void consumePullRequestEvents(String value) {
    log.info(
        "[Consumer] Consumed event body for event type {} : {}",
        KafkaConstants.PR_EDIT_TOPIC_NAME,
        value);
    // TODO: Configure AI tools to analyze the submitted code review.
  }
}
