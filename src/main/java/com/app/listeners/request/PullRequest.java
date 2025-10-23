package com.app.listeners.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class PullRequest {
  private String url;
  private String id;

  @JsonProperty("node_id")
  private String nodeId;

  @JsonProperty("html_url")
  private String htmlUrl;

  @JsonProperty("diff_url")
  private String diffUrl;

  @JsonProperty("patch_url")
  private String patchUrl;

  @JsonProperty("issue_url")
  private String issueUrl;

  private String number;
  private String state;
  private String locked;
  private String title;
  private GithubUserDto user;
  private String body;

  @JsonProperty("created_at")
  private String createdAt;

  @JsonProperty("updated_at")
  private String updatedAt;

  @JsonProperty("closed_at")
  private String closedAt;

  @JsonProperty("merged_at")
  private String mergedAt;

  private String mergeCommitSha;
  private Object assignee;
  private Object[] assignees;
  private Object[] requestedReviewers;
  private Object[] requestedTeams;
  private Object[] labels;
  private Object milestone;
  private Boolean draft;

  @JsonProperty("commits_url")
  private String commitsUrl;

  private String reviewCommentsUrl;
  private String commentsUrl;
  private String statusesUrl;
}
