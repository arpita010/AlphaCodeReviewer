package com.app.config;

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
}
