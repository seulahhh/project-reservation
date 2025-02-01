//package com.project.member.web.api;
//
//import com.project.member.service.CustomerService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//@RequestMapping("/customer")
//@RequiredArgsConstructor
//public class CustomerApiController {
//    private final CustomerService customerService;
//
//
//    /**
//     * sortby 조건별로 리스트 rendering dd
//     * @param sortby
//     * @param lat
//     * @param lnt
//     * @param model
//     * @return
//     */
////    @GetMapping("/stores")
////    public String getStoresSortBy (
////            @RequestParam String sortby, @RequestParam Double lat, @RequestParam Double lnt,
////            Model model) {
////        List<StoreDto> results = new ArrayList<>();
////
////        if (sortby.isEmpty()) {
////
////        }
////        if (sortby.equals("name")) {
////            results = storeService.showOrderByNameWithDistance(LocationDto.of(lat, lnt));
////        } else if (sortby.equals("distance")) {
////            results = storeService.showOrderByDistanceAsc(LocationDto.of(lat, lnt));
////        } else if (sortby.equals("rating")) {
////            results = storeService.showOrderByRatingWithDistance(LocationDto.of(lat, lnt));
////        }
////        model.addAttribute(results);
////        model.addAttribute("location", LocationDto.of(lat, lnt)); // 위치 dto 도 함께 내려줌 (이후 다른 정렬순으로 조회할때 사용필요)
////        return "customer/store";
////    }
//
////    /**
////     * 매장 상세 정보 보여주기
////     * - 선택한 매장의
////     */d
////    @GetMapping("/stores/detail/{storeId}")
////    public ModelAndView storeDetailPage (@PathVariable Long storeId) {
////        StoreDto storeDto = storeService.showStoreDetail(storeId);
////        ModelAndView mv = new ModelAndView("store/store-detail");
////        mv.addObject(storeDto);
////        return mv;
////    }
////
////    /**
////     * 리뷰 작성하기
////     * - 선택한 매장에
////     */
////    @PostMapping("/stores/reviews/new")
////    public ModelAndView createReview (ReviewDto reviewDto, ModelAndView mv) {
////        customerService.createReview(reviewDto);
////
////        mv.setViewName("success");
////        return mv;
////    }
//
////    /**
////     * 예약하기
////     */

//    @PostMapping("/reservation/{storeId}")
//    public String createReservation (@PathVariable Long storeId, @ModelAttribute CreateReservationForm form, Model model) {
//        form.setCustomerId(customerService.getCurrentCustomerId());
//        form.setManagerId(managerService.getManagerIdFromStoreId(storeId));
//        form.setStoreId(storeId);
//
//        ReservationDto reservationDto = customerService.createReservation(form);
//
//        model.addAttribute(reservationDto);
//
//        return "customer/reservation-complete";
//    }
//}
