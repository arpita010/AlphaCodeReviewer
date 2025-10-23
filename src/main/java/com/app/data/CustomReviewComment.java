package com.app.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomReviewComment {
  private String file;
  private String startLineNumber;
  private String endLineNumber;
  private Boolean isBlockingComment;
  private String comment;
}
