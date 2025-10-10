package com.app.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubCommitResponse {
  private String sha;

  @JsonProperty("node_id")
  private String nodeId;

  private String url;

  @JsonProperty("html_url")
  private String htmlUrl;

  private GithubAuthorResponse author;
  private GithubCommiterResponse committer;
  private GithubTreeResponse tree;
  private String message;
  private List<GithubParentResponse> parents;
  private GithubVerificationResponse verification;
}
