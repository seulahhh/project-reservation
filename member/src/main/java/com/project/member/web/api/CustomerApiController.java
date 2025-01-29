package com.project.member.web.api;

import com.project.member.model.dto.LocationDto;
import com.project.member.model.dto.ReservationDto;
import com.project.member.model.dto.ReviewDto;
import com.project.member.model.dto.StoreDto;
import com.project.member.model.dto.form.CreateReservationForm;
import com.project.member.persistence.repository.StoreRepository;
import com.project.member.service.CustomerService;
import com.project.member.service.ManagerService;
import com.project.member.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerApiController {
    private final CustomerService customerService;
    private final StoreService storeService;
    private final StoreRepository storeRepository;
    private final ManagerService managerService;

    /**
     * 매장 보여주기 (이름순, 별점순)
     */
    @GetMapping("/stores/search")
    public ModelAndView searchStoresSortBy (
            @RequestParam Map<String, Object> requestMap) {
        ModelAndView mv = new ModelAndView();
        List<StoreDto> resultList = new ArrayList<>();
        if (requestMap.get("sort")
                      .equals("rating")) {
            resultList.addAll(storeService.showOrderByRatingAsc());
        } else { // 기본 정렬은 이름순
            resultList.addAll(storeService.showOrderByNameAsc());
        }
        mv.setViewName("customer/store");
        mv.addObject(resultList);
        return mv;
    }

    /**
     * 매장 보여주기 (거리순)
     */
    @GetMapping("/stores/search/distance")
    public String searchStoresSortByDistance (@RequestParam Double lat,
            @RequestParam Double lnt, Model model) {
        List<StoreDto> resultList =
                storeService.showOrderByDistanceAsc(LocationDto.of(lat, lnt));
        model.addAttribute("processedData", resultList);

        return "customer/storeFragments";
    }

    /**
     * 매장 상세 정보 보여주기
     * - 선택한 매장의
     */
    @GetMapping("/stores/detail/{storeId}")
    public ModelAndView storeDetailPage (@PathVariable Long storeId) {
        StoreDto storeDto = storeService.showStoreDetail(storeId);
        ModelAndView mv = new ModelAndView("store/store-detail");
        mv.addObject(storeDto);
        return mv;
    }

    /**
     * 리뷰 작성하기
     * - 선택한 매장에
     */
    @PostMapping("/stores/reviews/new")
    public ModelAndView createReview (ReviewDto reviewDto, ModelAndView mv) {
        customerService.createReview(reviewDto);

        mv.setViewName("success");
        return mv;
    }

    /**
     * 예약하기
     */
    @PostMapping("/reservation/{storeId}")
    public String createReservation (@PathVariable Long storeId, @ModelAttribute CreateReservationForm form, Model model) {
        form.setCustomerId(customerService.getCurrentCustomerId());
        form.setManagerId(managerService.getManagerIdFromStoreId(storeId));
        form.setStoreId(storeId);

        ReservationDto reservationDto = customerService.createReservation(form);

        model.addAttribute(reservationDto);

        return "customer/reservation-complete";
    }
}
