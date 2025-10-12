package com.app.services;

import com.app.constants.KafkaConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PullRequestListenerService {
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final KafkaService kafkaService;

  public void publishEvent(Map<String, Object> requestBody) throws Exception {
    String messageBody = objectMapper.writeValueAsString(requestBody);
    log.info("Stringified Message Body for Kafka Event : {}", messageBody);
    kafkaService.sendMessage(KafkaConstants.PR_EDIT_TOPIC_NAME, messageBody);
  }
}
