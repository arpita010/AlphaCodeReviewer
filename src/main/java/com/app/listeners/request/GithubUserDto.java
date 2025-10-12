package com.app.listeners.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubUserDto {
  private String login;
  private String id;
  private String nodeId;
  private String avatarUrl;
  private String url;
  private String htmlUrl;
  private String followersUrl;
  private String followingUrl;
  private String gistsUrl;
  private String starredUrl;
  private String subscriptionsUrl;
  private String organizationsUrl;
  private String reposUrl;
  private String receivedEventsUrl;
  private String type;
  private String userViewType;
  private String siteAdmin;
}
