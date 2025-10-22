package com.app.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodeDiffFetcherService {
  private final RestTemplate restTemplate;
  private HttpHeaders httpHeaders = new HttpHeaders();

  @PostConstruct
  void setHttpHeaders() {
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
  }

  public String fetchDiff(String diffUrl) {
    try {
      ResponseEntity<String> response =
          restTemplate.exchange(
              diffUrl, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
      log.info("Response for Diff URL {} : {}", diffUrl, response.getBody());
      return response.getBody();
    } catch (Exception e) {
      log.error("Error occurred while fetching diff from url {} : {}", diffUrl, e.getMessage());
    }
    return null;
  }
}
