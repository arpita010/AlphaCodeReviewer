package com.app.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubFileUploadResponse {
  private GithubContentResponse content;
  private GithubCommitResponse commit;
}
