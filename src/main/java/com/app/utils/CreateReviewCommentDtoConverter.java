package com.app.utils;

import com.app.data.CreateReviewCommentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateReviewCommentDtoConverter implements Converter<CreateReviewCommentDto> {
  private final ObjectMapper mapper;

  @Override
  public String serialize(CreateReviewCommentDto message) {
    try {
      String response = mapper.writeValueAsString(message);
      return response;
    } catch (Exception e) {
      log.error(
          "Error occurred while converting message for " + "createReviewCommentDto: {}",
          e.getMessage());
    }
    return null;
  }

  @Override
  public CreateReviewCommentDto deserialize(String message) {
    CreateReviewCommentDto createReviewCommentDto = null;
    try {
      createReviewCommentDto = mapper.readValue(message, CreateReviewCommentDto.class);
    } catch (Exception e) {
      log.error(
          "Error occurred while deserializing message for " + "createReviewCommentDto: {}",
          e.getMessage());
    }
    return createReviewCommentDto;
  }
}
