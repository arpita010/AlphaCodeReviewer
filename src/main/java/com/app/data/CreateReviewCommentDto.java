package com.app.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateReviewCommentDto {
  private String issueNumber;
  private String fullName;
  private String title;
  private String body;
  private String modelResponse; // it will contain our custom converted json
  // response only.
}
