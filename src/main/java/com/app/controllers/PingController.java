package com.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/health")
@Slf4j
public class PingController {

    @GetMapping("")
    public String get() {
        log.info("Service Ping Request Received at timestamp : {}", System.currentTimeMillis());
        return "Service is healthy";
    }
}
