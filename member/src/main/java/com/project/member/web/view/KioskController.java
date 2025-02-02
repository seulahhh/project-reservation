package com.project.member.web.view;

import com.project.global.dto.ReservationDto;
import com.project.global.dto.form.ArrivalCheckForm;
import com.project.global.dto.form.KioskForm;
import com.project.member.client.ReservationApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/kiosk")
@RequiredArgsConstructor
public class KioskController {
    private final ReservationApiClient reservationApiClient;

    /**
     * 키오스크 메인페이지
     */
    @GetMapping
    public String kioskPage () {
        return "kiosk";
    }

    /**
     * 키오스크에서 매장에 대한 고객의 예약 내역 조회
     */
    @GetMapping("/reservations")
    public String getCustomerStoreReservations(@ModelAttribute KioskForm form, Model model) {
        log.info("{}",form.toString());
        List<ReservationDto> reservations = reservationApiClient.getCustomerStoreReservations(form);
        model.addAttribute("reservations", reservations);
        return "customer/reservation-list";
    }

    /**
     * 도착 확인 체크 하기
     */
    @PostMapping("/check")
    public String updateArrivalStatus(@ModelAttribute ArrivalCheckForm form) {
        reservationApiClient.updateArrivalStatus(form);
        return "redirect:/customer/customer";
    }
}
