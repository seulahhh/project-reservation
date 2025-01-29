package com.project.member.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.infrastructure.kafka.KafkaProducer;
import com.project.infrastructure.kafka.dto.ReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaTestController {
    private final KafkaProducer kafkaProducer;

    @GetMapping("/customer/kafka/test")
    public void kafkaTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(ReservationDto.builder()
                                                           .id(2L)
                                                           .customerId(2L)
                                                           .build());
        kafkaProducer.sendMessage("toManager", s);
    }
}
