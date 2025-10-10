package com.app.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubParentResponse {
  private String sha;
  private String url;

  @JsonProperty("html_url")
  private String htmlUrl;
}
