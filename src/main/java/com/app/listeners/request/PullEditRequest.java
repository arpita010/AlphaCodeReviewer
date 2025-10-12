package com.app.listeners.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PullEditRequest {
  private String action;
  private String number;

  @JsonProperty("pull_request")
  private PullRequest pullRequest;
}
