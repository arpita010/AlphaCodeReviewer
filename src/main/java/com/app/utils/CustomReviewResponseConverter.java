package com.app.utils;

import com.app.data.CustomReviewResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomReviewResponseConverter implements Converter<List<CustomReviewResponse>> {
  private final ObjectMapper mapper;

  @Override
  public String serialize(List<CustomReviewResponse> customReviewResponses) {
    String message = "";
    try {
      message = mapper.writeValueAsString(customReviewResponses);
      log.info("Custom Review Responses in JSON string format : {}", message);
    } catch (Exception e) {
      log.error("Error occurred while writing custom review responses : {}", e.getMessage());
    }
    return message;
  }

  @Override
  public List<CustomReviewResponse> deserialize(String message) {

    List<CustomReviewResponse> customReviewResponses = null;
    try {
      customReviewResponses =
          mapper.readValue(message, new TypeReference<List<CustomReviewResponse>>() {});
    } catch (Exception e) {
      log.error(
          "Error occurred while deserializing custom review responses :" + " {}", e.getMessage());
    }
    return customReviewResponses;
  }
}
