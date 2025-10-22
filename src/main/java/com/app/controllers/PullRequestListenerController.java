package com.app.controllers;

// Webhook for listening pull requests

import com.app.listeners.request.PullEditRequest;
import com.app.listeners.response.PullListenerResponse;
import com.app.services.PullListenerService;
import com.app.services.PullListenerServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/pull")
@ResponseBody
@RequiredArgsConstructor
public class PullRequestListenerController {
  private final PullListenerService pullRequestListenerService;

  //  @PostMapping("/listen")
  //  public ResponseEntity<?> listen(@RequestBody Map<String, Object> request) throws Exception {
  //    pullRequestListenerService.publishEvent(request);
  //    return ResponseEntity.status(HttpStatus.ACCEPTED)
  //        .body(PullRequestListenerResponse.builder().status("Success").build());
  //  }

  //    @GetMapping("/listen")
  //    public ResponseEntity<?> listen() {
  //        log.info("Pull request received...");
  //        log.info("---------------");
  //        log.info("Request Body for webhook : ");
  //        log.info("---------------");
  //        return ResponseEntity.status(HttpStatus.ACCEPTED)
  //                .body(PullRequestListenerResponse.builder().status("Success").build());
  //    }

  @PostMapping("/listen")
  public ResponseEntity<PullListenerResponse> getPullRequestListenerResponse(
      @RequestBody PullEditRequest request) {
    PullListenerResponse response = pullRequestListenerService.publishEvent(request);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
  }
}
