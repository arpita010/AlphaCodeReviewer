package com.app.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubLinkResponse {
  private String self;
  private String git;
  private String html;
}
