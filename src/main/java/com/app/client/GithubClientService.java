package com.app.client;

import com.app.client.request.CreateReviewCommentRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubClientService {
  private final RestTemplate restTemplate;
  private HttpHeaders headers = new HttpHeaders();
  private final GithubConfig config;

  @PostConstruct
  public void initialize() {
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(config.getGithubToken());
  }

  public void publishComment(
      CreateReviewCommentRequest request, String fullName, String issueNumber) {
    String url =
        config
            .getCreateReviewCommentUrl()
            .replace("{fullName}", fullName)
            .replace("{issueNumber}", issueNumber);
    log.info("Request to publish comment to Github for url : {}", url);
    try {
      ResponseEntity<String> response =
          restTemplate.exchange(
              url, HttpMethod.POST, new HttpEntity<>(request, headers), String.class);
    } catch (Exception e) {
      log.error(
          "Error occurred while publishing comment for full name {}, issue " + "number {} : {} ",
          fullName,
          issueNumber,
          e.getMessage());
    }
  }
}
