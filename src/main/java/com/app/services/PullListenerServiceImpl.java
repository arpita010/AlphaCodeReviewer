package com.app.services;

import com.app.constants.ApiResponse;
import com.app.constants.KafkaTopicName;
import com.app.listeners.request.PullEditRequest;
import com.app.listeners.response.PullListenerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PullListenerServiceImpl implements PullListenerService {
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final KafkaService kafkaService;

  //  public void publishEvent(Map<String, Object> requestBody) throws Exception {
  //    String messageBody = objectMapper.writeValueAsString(requestBody);
  //    log.info("Stringified Message Body for Kafka Event : {}", messageBody);
  //    kafkaService.sendMessage(KafkaConstants.PR_EDIT_TOPIC_NAME, messageBody);
  //  }

  public PullListenerResponse publishEvent(PullEditRequest request) {
    try {
      String requestJson = objectMapper.writeValueAsString(request);
      kafkaService.publishEvent(KafkaTopicName.PULL_REQUEST, requestJson);
    } catch (Exception e) {
      log.error("Exception occured while publishing kafka event : {}", e.getMessage());
      return PullListenerResponse.builder()
          .status(ApiResponse.FAILED)
          .message(e.getMessage())
          .build();
    }
    return PullListenerResponse.builder().status(ApiResponse.SUCCESS).build();
  }
}
