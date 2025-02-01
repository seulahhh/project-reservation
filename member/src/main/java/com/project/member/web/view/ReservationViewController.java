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
    @GetMapping("/customer/reservation/new/{storeId}")
    public String createReservationPage(@PathVariable Long storeId, Model model) {
        // todo 예약하기 버튼에 storeId mapping해서 해당 페이지로 진입할 수 있도로 ㄱ하기
        return "customer/reservation";
    }

    @GetMapping("/customer/reservation")
    public String reservationPage() {
        return "customer/reservation";
    }
}
