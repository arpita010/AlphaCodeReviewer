package com.app.client;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class GithubConfig {
  @Value("${github.token}")
  private String githubToken;

  @Value("${create.review.comment.url}")
  private String createReviewCommentUrl;
}
