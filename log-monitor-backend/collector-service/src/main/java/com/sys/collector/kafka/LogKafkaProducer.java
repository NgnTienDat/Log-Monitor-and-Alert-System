package com.sys.collector.kafka;

import com.sys.core.dto.LogEntryDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogKafkaProducer {

    private final KafkaTemplate<String, LogEntryDTO> kafkaTemplate;

    @Value("${app.kafka.topics.raw-logs}")
    private String rawLogsTopic;

    public void send(LogEntryDTO logEntry) {
        kafkaTemplate.send(rawLogsTopic, resolveKey(logEntry), logEntry);
    }

    private String resolveKey(LogEntryDTO logEntry) {
        if (logEntry.getTraceId() != null && !logEntry.getTraceId().isBlank()) {
            return logEntry.getTraceId();
        }
        // Trả về null để Kafka tự động rải đều (Round-robin) giúp cân bằng tải
        // Nếu dùng applicationName làm key, có thể gây ra tình trạng một partition bị quá tải
        return null;
    }
}
