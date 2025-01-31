package com.project.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * message producer
 */
@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message, Long id) {
        kafkaTemplate.send(topic, String.valueOf(id), message);
        System.out.println("Sent mesasge: " + message + id);
    }
}
