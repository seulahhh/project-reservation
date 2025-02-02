package com.project.member.web.api;

import com.project.global.dto.ReservationDto;
import com.project.member.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class SseConnectController {
    private final AlarmService alarmService;
    @GetMapping("/sse-connect")
    public String sendConnectToServer(Model model) {
//        ReservationDto reservationDto = alarmService.sendConnectToServer();
//        model.addAttribute(reservationDto);
        return "notification-fragment :: notification-message";
    }
}
