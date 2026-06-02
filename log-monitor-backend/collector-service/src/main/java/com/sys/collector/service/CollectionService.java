package com.sys.collector.service;

import com.sys.collector.kafka.LogKafkaProducer;
import com.sys.core.dto.LogEntryDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CollectionService {

    LogKafkaProducer logKafkaProducer;

    public int collect(List<LogEntryDTO> logEntries) {
        if (logEntries == null || logEntries.isEmpty()) {
            return 0;
        }

        int acceptedCount = 0;
        for (LogEntryDTO logEntry : logEntries) {
            if (logEntry == null) {
                continue;
            }

            logKafkaProducer.send(logEntry);
            acceptedCount++;
        }

        return acceptedCount;
    }
}
