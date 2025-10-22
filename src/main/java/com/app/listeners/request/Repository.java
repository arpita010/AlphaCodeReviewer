package com.app.listeners.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository {
  private String id;

  @JsonProperty("node_id")
  private String nodeId;

  private String name;

  @JsonProperty("full_name")
  private String fullName; // username+repoName
}
