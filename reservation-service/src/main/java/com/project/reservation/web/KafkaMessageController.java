package com.project.reservation.web;

//@RestController
//@RequiredArgsConstructor
//public class KafkaMessageController {
//    private final KafkaProducer kafkaProducer;
//
//    @GetMapping("/cafka/kafka/test")
//    public void kafkaTest() throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String s = objectMapper.writeValueAsString(ReservationDto.builder()
//                                                           .build());
//        kafkaProducer.sendMessage("toManager", s);
//    }
//}
