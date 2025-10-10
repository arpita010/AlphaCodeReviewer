package com.app.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubVerificationResponse {
  private Boolean verified;
  private String reason;
  private Object signature;
  private Object payload;
}
