package com.project.reservation.alarm;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SseController {
    private final SseEmitterService sseEmitterService;

    @GetMapping("/sse-connect")
    public SseEmitter streamMessages(@RequestParam Long id) {
        return sseEmitterService.streamMessages(id);
    }
}
