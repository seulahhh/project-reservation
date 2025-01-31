package com.project.member.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.global.dto.ReservationDto;
import com.project.member.config.sse.SseController;
import com.project.member.config.sse.SseEmitterService;
import com.project.member.persistence.entity.QManager;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Manager가 알람을 listen 하는 매체
 */
@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final SseEmitterService sseEmitterService;
    @KafkaListener(topics = "toManager", groupId = "test-consumer-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println(record.value());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            ReservationDto reservationDto = objectMapper.readValue(record.value(), ReservationDto.class);
            sseEmitterService.sendMessages(Long.parseLong(record.key()), reservationDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}