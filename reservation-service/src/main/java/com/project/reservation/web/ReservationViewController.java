package com.project.reservation.web;

import com.project.member.model.dto.StoreDto;
import com.project.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ReservationViewController {
    private final ReservationService reservationService;
//    @GetMapping("/customer/stores/{storeId}/reservation")
//    public String reservationPage(@PathVariable Long storeId, Model model) {
//        StoreDto storeDto = reservationService.getStoreDtoByStoreId(storeId);
//        model.addAttribute(storeDto);
//        return "reservation";
//    }
}
