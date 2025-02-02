package com.project.member.config.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * sse 연결 요청 url 엔드포인트 및 그 컨트롤러 정의
 */
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class SseController {
    private final SseEmitterService sseEmitterService;

    @GetMapping("/sse-connect/{managerId}")
    public SseEmitter streamMessages (@PathVariable Long managerId, Model model) {
        model.addAttribute(managerId);
        return sseEmitterService.streamMessages(managerId);
    }
}
