package com.app.services;

import com.app.constants.KafkaTopicName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {
  private final KafkaTemplate<String, String> kafkaTemplate;

  public void sendMessage(String topic, String message) {
    kafkaTemplate.send(topic, message);
    log.info("Message sent to topic " + topic + " : " + message + " " + "Successfully");
  }

  public void publishEvent(KafkaTopicName topic, String message) {
    log.info("Publishing event to topic {} with message {}", topic, message);
    kafkaTemplate.send(topic.toString(), message);
    log.info("Message published to topic {} with message {}", topic, message);
  }
}
