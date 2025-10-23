package com.app.consumers;

import com.app.client.GithubClientService;
import com.app.client.request.CreateReviewCommentRequest;
import com.app.constants.ConverterType;
import com.app.constants.KafkaConstants;
import com.app.constants.ReviewSide;
import com.app.data.CreateReviewCommentDto;
import com.app.data.CustomReviewComment;
import com.app.data.CustomReviewResponse;
import com.app.factory.ConverterFactory;
import com.app.utils.Converter;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PublishReviewCommentConsumer {
  private Converter<CreateReviewCommentDto> createReviewCommentDtoConverter;
  private Converter<List<CustomReviewResponse>> customReviewResponseConverter;
  private final ConverterFactory factory;
  private final GithubClientService githubClientService;

  @PostConstruct
  public void initialize() {
    this.createReviewCommentDtoConverter =
        factory.getConverter(ConverterType.CREATE_REVIEW_COMMENT_DTO_CONVERTER);
    this.customReviewResponseConverter =
        factory.getConverter(ConverterType.CUSTOM_REVIEW_RESPONSE_CONVERTER);
  }

  @KafkaListener(topics = KafkaConstants.CREATE_REVIEW_COMMENT, groupId = "group-id-1")
  public void consume(String message, Acknowledgment acknowledgment) {
    acknowledgment.acknowledge();
    log.info("Start consuming create review comment event {}", message);
    processMessage(message);
    log.info("Consumed create review comment event {}", message);
  }

  private void processMessage(String message) {
    CreateReviewCommentDto reviewComment = createReviewCommentDtoConverter.deserialize(message);
    List<CustomReviewResponse> customReviewResponses =
        customReviewResponseConverter.deserialize(reviewComment.getModelResponse());
    for (CustomReviewResponse customReviewResponse : customReviewResponses) {
      publishReviewComments(customReviewResponse, reviewComment);
    }
  }

  private void publishReviewComments(
      CustomReviewResponse customReviewResponse, CreateReviewCommentDto reviewComment) {
    for (CustomReviewComment comment : customReviewResponse.getComments()) {
      CreateReviewCommentRequest request = createReviewCommentRequest(comment, reviewComment);
      githubClientService.publishComment(
          request, reviewComment.getFullName(), reviewComment.getIssueNumber());
    }
  }

  private CreateReviewCommentRequest createReviewCommentRequest(
      CustomReviewComment comment, CreateReviewCommentDto reviewComment) {
    Integer lines =
        Integer.parseInt(comment.getEndLineNumber())
            - Integer.parseInt(comment.getStartLineNumber())
            + 1;
    CreateReviewCommentRequest request =
        CreateReviewCommentRequest.builder()
            .body(comment.getComment())
            .commitId(reviewComment.getLastCommitSha())
            .startLine(Integer.parseInt(comment.getStartLineNumber()))
            .line(lines)
            .path(comment.getFile())
            .side(ReviewSide.RIGHT.getSide())
            .startSide(ReviewSide.RIGHT.getSide())
            .build();
    return request;
  }
}
