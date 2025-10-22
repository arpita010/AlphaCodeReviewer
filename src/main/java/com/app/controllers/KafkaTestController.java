package com.app.controllers;

import com.app.services.KafkaService;
import com.app.services.KafkaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
@RequestMapping("/message")
@RequiredArgsConstructor
public class KafkaTestController {
  private final KafkaService kafkaService;

  @GetMapping
  public String post() {
    kafkaService.sendMessage("my-first-topic", "message with time: " + System.currentTimeMillis());
    return "Message Sent!";
  }
}
