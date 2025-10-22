package com.app.consumers;

import com.app.constants.KafkaConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@Configuration
public class PullEventConsumer {

  @KafkaListener(topics = KafkaConstants.PULL_REQUEST)
  public void consumePullEvent(String message) {
    log.info(
        "Consuming pull event message for topic {} : {}", KafkaConstants.PULL_REQUEST, message);
    // TODO: Configure AI tools to analyze the submitted code review.
  }
}
