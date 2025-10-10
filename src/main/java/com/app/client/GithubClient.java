package com.app.client;

import jakarta.annotation.PostConstruct;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class GithubClient {
    private final GithubConfig githubConfig;
    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    @PostConstruct
    public void setApiHeaders() {
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(githubConfig.getGithubToken());
    }

    public String uploadFile(MultipartFile file, String fileName, String repoName) throws Exception {
        if (file == null) return null;
        //    log.info("File name : {}", fileName);
        //    log.info("Repo name : {}", repoName);
        //    log.info("Owner : {}", githubConfig.getRepoOwner());
        String apiUrl =
                githubConfig
                        .getGITHUB_API_URL()
                        .replace("{owner}", githubConfig.getRepoOwner())
                        .replace("{repo}", repoName)
                        .replace("{path}", fileName);
        try {
            String fileContent = Base64.getEncoder().encodeToString(file.getBytes());
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("message", "Upload file: " + fileName);
            requestBody.put("content", fileContent);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response =
                    restTemplate.exchange(apiUrl, HttpMethod.PUT, entity, String.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                return response.getBody();
            }
        } catch (Exception e) {
            log.error("Error while uploading file to GITHUB : {}", e.getMessage());
        }
        return null;
    }
}