package com.app.client;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class GithubConfig {
    @Value("${github.token}")
    private String githubToken;

    @Value("${github.repo.owner}")
    private String repoOwner;

    //  @Value("${github.repo.name}")
    private String repoName;

    @Value("${github.repo.branch}")
    private String repoBranch;

    private String GITHUB_API_URL = "https://api.github.com/repos/{owner}/{repo}/contents/{path}";
}