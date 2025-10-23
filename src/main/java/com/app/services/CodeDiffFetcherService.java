package com.app.services;

import com.app.client.response.commits.CommitResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodeDiffFetcherService {
  private final RestTemplate restTemplate;
  private HttpHeaders httpHeaders = new HttpHeaders();
  private final ObjectMapper mapper;

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

  public String getLastCommitSha(String commitsUrl) {
    List<CommitResponse> commits = fetchCommits(commitsUrl);
    if (commits == null || commits.isEmpty()) return null;
    return commits.get(commits.size() - 1).getSha();
  }

  private List<CommitResponse> fetchCommits(String commitsUrl) {
    try {
      ResponseEntity<String> response =
          restTemplate.exchange(
              commitsUrl, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
      log.info("Response for Commits URL {} : {}", commitsUrl, response.getBody());
      List<CommitResponse> list =
          mapper.readValue(response.getBody(), new TypeReference<List<CommitResponse>>() {});
      return list;
    } catch (Exception e) {
      log.error(
          "Error occurred while fetching commits from url {} : {}", commitsUrl, e.getMessage());
    }
    return null;
  }
}
