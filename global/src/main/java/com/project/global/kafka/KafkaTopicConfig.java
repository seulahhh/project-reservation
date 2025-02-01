package com.project.global.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Kafka topic 생성
 * manager로 가는 메세지, customer로 가는 메세지를 각 토픽으로 발행
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * Customer로 전달되는 메세지
     * @return NewTopic
     */
    @Bean
    public NewTopic toCustomer() {
        return TopicBuilder.name("toCustomer")
                .partitions(2)
                .replicas(1)
                .config(
                        TopicConfig.RETENTION_MS_CONFIG,
                        String.valueOf(7* 24 * 60 * 60 * 1000L) // 7일
                )
                .build();
    }

    /**
     * Manager로 전달되는 메세지
     * @return NewTopic
     */
    @Bean
    public NewTopic toManager() {
        return TopicBuilder.name("toManager")
                           .partitions(2)
                           .replicas(1)
                           .config(
                                   TopicConfig.RETENTION_MS_CONFIG,
                                   String.valueOf(7* 24 * 60 * 60 * 1000L) // 7일
                           )
                           .build();
    }
}
