package com.dev.logsapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @GetMapping
    public ResponseEntity<String> test() {
        log.info("Log 1: Received GET request to /api/v1/collections/test");
        log.error("Log 2: Received GET request to /api/v1/collections/test", new RuntimeException("Test exception for logging"));
        return ResponseEntity.ok("Hello, " + "!");
    }
}
