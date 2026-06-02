package com.sys.collector.controller;

import com.sys.collector.service.CollectionService;
import com.sys.core.dto.LogEntryDTO;
import com.sys.core.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@RestController
@RequestMapping("/api/v1/collections")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CollectionController {

    CollectionService collectionService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        log.info("Log 1: Received GET request to /api/v1/collections/test");
        log.error("Log 2: Received GET request to /api/v1/collections/test", new RuntimeException("Test exception for logging"));
        return ResponseEntity.ok("Hello, " + "!");
    }

    @GetMapping("/test2")
    public ResponseEntity<String> test2() {
        return ResponseEntity.ok("Hello world " + "!");
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CollectionResponse>> collect(
            @RequestBody @Valid List<LogEntryDTO> logEntries) {
        int acceptedCount = collectionService.collect(logEntries);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(new CollectionResponse(acceptedCount)));
    }

    public record CollectionResponse(int acceptedCount) {
    }
}
