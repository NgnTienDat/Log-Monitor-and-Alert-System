package com.sys.core.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogEntryDTO implements Serializable {
    String traceId;
    String applicationName;
    String logLevel;
    String message;
    Long timestamp;
    String status;
}
