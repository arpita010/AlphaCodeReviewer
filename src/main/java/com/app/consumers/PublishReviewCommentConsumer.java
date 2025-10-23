package com.app.consumers;

import com.app.constants.ConverterType;
import com.app.constants.KafkaConstants;
import com.app.data.CreateReviewCommentDto;
import com.app.data.CustomReviewResponse;
import com.app.factory.ConverterFactory;
import com.app.utils.Converter;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PublishReviewCommentConsumer {
  private Converter<CreateReviewCommentDto> createReviewCommentDtoConverter;
  private Converter<List<CustomReviewResponse>> customReviewResponseConverter;
  private final ConverterFactory factory;

  @PostConstruct
  public void initialize() {
    this.createReviewCommentDtoConverter =
        factory.getConverter(ConverterType.CREATE_REVIEW_COMMENT_DTO_CONVERTER);
    this.customReviewResponseConverter =
        factory.getConverter(ConverterType.CUSTOM_REVIEW_RESPONSE_CONVERTER);
  }

  @KafkaListener(topics = KafkaConstants.CREATE_REVIEW_COMMENT)
  public void consume(String message) {
    log.info("Start consuming create review comment event {}", message);
    processMessage(message);
    log.info("Consumed create review comment event {}", message);
  }

  private void processMessage(String message) {
    CreateReviewCommentDto reviewComment = createReviewCommentDtoConverter.deserialize(message);
    List<CustomReviewResponse> customReviewResponses =
        customReviewResponseConverter.deserialize(reviewComment.getModelResponse());
  }
}
