package com.app.consumers;

import com.app.constants.KafkaConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@Configuration
public class PublishReviewCommentConsumer {

  @KafkaListener(topics = KafkaConstants.CREATE_REVIEW_COMMENT)
  public void consume(String message) {
    log.info("Start consuming create review comment event {}", message);

    log.info("Consumed create review comment event {}", message);
  }
}
