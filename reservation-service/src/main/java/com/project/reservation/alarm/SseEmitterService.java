package com.project.reservation.alarm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.global.dto.ReservationDto;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseEmitterService {
    private ConcurrentHashMap<Long, SseEmitter> emitters =
            new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    /**
     * reservation service - manager 서버 최초 연결
     * (로그인시 trigger)
     * @param clientId manager id
     * @return 알람 전송 eventlisteer
     */
    public SseEmitter streamMessages (Long clientId) {
        SseEmitter emitter = new SseEmitter();

        // 연결이 완료되었을 때 호출됨
        emitter.onCompletion(() -> {
            emitters.put(clientId, emitter);
            System.out.println("Client has completed the connection.");
        });
        // 타임아웃 발생 시 호출되는 메서드
        emitter.onTimeout(() -> {
            emitters.remove(clientId, emitter);
            System.out.println("Client connection timed out.");
            emitter.complete();  // 타임아웃 시 연결 종료
        });
        // 클라이언트 연결이 끊어졌을 때 처리
        emitter.onError((ex) -> {
            emitters.remove(clientId);
            System.out.println("Error occurred: " + ex.getMessage());
            emitter.completeWithError(ex);  // 에러 발생 시 연결 종료
        });

        new Thread(() -> {
            try {
                emitter.send("Welcome to the real-time notification system!");
                emitters.put(clientId, emitter);
            } catch (Exception e) {
                emitter.completeWithError(e);  // 오류 발생 시 처리
            }
        }).start();

        return emitter;
    }

    /**
     * Manager에게 알람 전송하기
     */
    public boolean sendMessages(Long clientId, ReservationDto reservationDto) {
        SseEmitter targetE;
        System.out.println(clientId);

        if (emitters.containsKey(clientId)) {
            targetE = emitters.get(clientId);
            try {
                objectMapper.writeValueAsString(reservationDto);
                targetE.send(reservationDto);
            } catch (Exception e) {
                targetE.completeWithError(e);  // 오류 발생 시 처리
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return true;
    }
}
