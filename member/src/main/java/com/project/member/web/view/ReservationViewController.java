package com.project.member.web.view;

import com.project.member.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ReservationViewController {
    private final CustomerService customerService;
    /**
     * 예약하는 페이지 진입
     * x
     */
    @GetMapping("/customer/reservations/new/{storeId}")
    public String createReservationPage(@PathVariable Long storeId, Model model) {
        return "customer/reservation";
    }

    /**
     * 예약하는페이지
     */
    @GetMapping("/customer/reservation/{storeId}")
    public String reservationPage(@PathVariable Long storeId, Model model) {
        model.addAttribute("storeId", storeId);
        return "customer/reservation";
    }

}
