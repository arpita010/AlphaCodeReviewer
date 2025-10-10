package com.app.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubContentResponse {
  private String name;
  private String path;
  private String sha;
  private String size;
  private String url;

  @JsonProperty("html_url")
  private String htmlUrl;

  @JsonProperty("download_url")
  private String downloadUrl;

  private String type;

  @JsonProperty("_links")
  private GithubLinkResponse links;
}
