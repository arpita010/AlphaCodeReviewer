package com.app.controllers;

// Webhook for listening pull requests

import com.app.data.PullRequestListenerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/pull")
@ResponseBody
public class PullRequestListener {

  @PostMapping("/listen")
  public ResponseEntity<?> listen(@RequestBody Map<String, Object> request) {
    log.info("Pull request received...");
    log.info("---------------");
    log.info("Request Body for webhook : {}", request);
    log.info("---------------");
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(PullRequestListenerResponse.builder().status("Success").build());
  }

//    @GetMapping("/listen")
//    public ResponseEntity<?> listen() {
//        log.info("Pull request received...");
//        log.info("---------------");
//        log.info("Request Body for webhook : ");
//        log.info("---------------");
//        return ResponseEntity.status(HttpStatus.ACCEPTED)
//                .body(PullRequestListenerResponse.builder().status("Success").build());
//    }
}
