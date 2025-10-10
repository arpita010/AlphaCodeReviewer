package com.app.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubCommiterResponse {
  private String name;
  private String email;
  private String date;
}
