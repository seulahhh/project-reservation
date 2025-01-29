package com.project.member.web;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Manager가 알람을 listen 하는 매체
 */
@Service
public class KafkaConsumer {
    @KafkaListener(topics = "toManager", groupId = "test-consumer-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message) {
        System.out.println(message);
    }

}