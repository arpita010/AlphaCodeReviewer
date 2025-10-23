package com.app.client.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewCommentRequest {
  /* https://docs.github.com/en/rest/pulls/comments?apiVersion=2022-11-28#create-a-review-comment-for-a-pull-request */
  private String body;

  @JsonProperty("commit_id")
  private String commitId;

  private String path;

  @JsonProperty("start_line")
  private Integer startLine;

  @JsonProperty("start_side")
  private String startSide;

  private Integer line;
  private String side;
}
