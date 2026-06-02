package com.sys.collector.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${app.kafka.topics.raw-logs}")
    private String rawLogsTopic;

    /**
     * Định nghĩa Topic để hứng log từ các service.
     * Spring Boot sẽ tự động gọi KafkaAdmin để tạo topic này khi khởi động app.
     */
    @Bean
    public NewTopic rawLogsTopic() {
        return TopicBuilder.name(rawLogsTopic)
                // CHIA PHÂN VÙNG (PARTITIONS): Cực kỳ quan trọng để Scale.
                // 3 Partitions nghĩa là sau này ta có thể chạy tối đa 3 con Logstash
                // song song để kéo data ra mà không bị đụng nhau.
                .partitions(3)

                // BẢN SAO (REPLICAS): Bằng 1 vì ta đang chạy 1 node Kafka trên Docker.
                // Lên Production (cụm 3 nodes), số này thường là 2 hoặc 3 để chống chết ổ cứng.
                .replicas(1)

                // Cấu hình nâng cao riêng cho topic này (Tùy chọn)
                .config("retention.ms", "86400000") // Giữ log thô tối đa 1 ngày (24h) rồi tự xóa
                .config("compression.type", "snappy")
                .build();
    }

//    @Bean
//    public NewTopic alertsTopic() {
//        return TopicBuilder.name(alertsTopic)
//                .partitions(3)
//                .replicas(1)
//                .config("retention.ms", "604800000") // Giữ cảnh báo tối đa 7 ngày (168h) rồi tự xóa
//                .config("compression.type", "snappy")
//                .build();
//    }
}