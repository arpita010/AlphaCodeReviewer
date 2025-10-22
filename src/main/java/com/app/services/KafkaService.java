package com.app.services;

import com.app.constants.KafkaTopicName;

public interface KafkaService {
  void publishEvent(KafkaTopicName topic, String message);

  void sendMessage(String topic, String message);
}
