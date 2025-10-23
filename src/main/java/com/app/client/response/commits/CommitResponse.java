package com.app.client.response.commits;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class CommitResponse {
  private String sha;

  @JsonProperty("node_id")
  private String nodeId;

  private Object commit;
  private Object url;

  @JsonProperty("html_url")
  private Object htmlUrl;

  @JsonProperty("comments_url")
  private Object commentsUrl;

  private Object author;
  private Object committer;
  private Object parents;
}
