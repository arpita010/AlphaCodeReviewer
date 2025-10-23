package com.app.factory;

import com.app.constants.ConverterType;
import com.app.utils.Converter;
import com.app.utils.CreateReviewCommentDtoConverter;
import com.app.utils.CustomReviewResponseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConverterFactory {
  private final CreateReviewCommentDtoConverter createReviewCommentDtoConverter;
  private final CustomReviewResponseConverter customReviewResponseConverter;

  public Converter getConverter(ConverterType type) {
    switch (type) {
      case CREATE_REVIEW_COMMENT_DTO_CONVERTER -> {
        return createReviewCommentDtoConverter;
      }
      case CUSTOM_REVIEW_RESPONSE_CONVERTER -> {
        return customReviewResponseConverter;
      }
      default -> {
        throw new IllegalArgumentException("Invalid converter type");
      }
    }
  }
}
