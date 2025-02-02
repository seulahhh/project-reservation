package com.project.member.config.sse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.global.dto.CompleteReservationDto;
import com.project.global.dto.ReservationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * sse 연결과 관련된 메세지를 전송하는 메서드 및
 * 받은 예약신청에 대한 메세지를 전송하는 메서드를 구현한 클래스
 */
@Slf4j
@Service
public class SseEmitterService {
    private ConcurrentHashMap<Long, SseEmitter> emitters =
            new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    /**
     * SSE 연결
     * 서버 - UI
     * 페이지를 옮길때마다 연결되어야 함 => 공통 javascript 파일을 두어 알람을 전송해야한다.
     * @param clientId manager id
     * @return 알람 전송 eventlisteer
     */
    public SseEmitter streamMessages (Long clientId) {
        SseEmitter emitter = new SseEmitter();
        emitters.put(clientId, emitter);

        // 연결이 완료되었을 때 호출됨
        emitter.onCompletion(() -> {
            emitters.remove(clientId, emitter);
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
                log.info("ready to listen alarm");
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
    public boolean sendMessages(Long clientId, CompleteReservationDto reservationDto) {
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
