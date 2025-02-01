package com.project.member.web;

//@RestController
//@RequiredArgsConstructor
//public class KafkaTestController {
//    private final KafkaProducer kafkaProducer;
//
//    @GetMapping("/customer/kafka/test")
//    public void kafkaTest() throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String s = objectMapper.writeValueAsString(ReservationDto.builder()
//                                                           .id(2L)
//                                                           .customerId(2L)
//                                                           .build());
//        kafkaProducer.sendMessage("toManager", s);
//    }
//}
