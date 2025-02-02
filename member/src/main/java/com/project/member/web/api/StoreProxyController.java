package com.project.member.web.api;

import com.project.global.dto.LocationDto;
import com.project.global.dto.StoreDto;
import com.project.global.dto.form.AddStoreForm;
import com.project.member.client.StoreApiClient;
import com.project.member.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StoreProxyController {
    private final StoreApiClient storeApiClient;
    private final ManagerService managerService;
    /**
     * 조건에 맞게 정렬하여 매장 리스트 보여주기
     */
    @GetMapping("/customer/stores")
    public String getStoreListOrderByDistance (
            @RequestParam String sortby, @RequestParam Double lat,
            @RequestParam Double lnt, Model model
    ) {
        List<StoreDto> list;
        LocationDto locationDto = LocationDto.of(lat, lnt);
        if (sortby.equals("distance")) {
            list = storeApiClient.getStoreListOrderByDistance(locationDto);
        } else if (sortby.equals("name")) {
            list = storeApiClient.getStoreListOrderByName(locationDto);
        } else {
            list = storeApiClient.getStoreListOrderByRating(locationDto);
        }
        model.addAttribute("locationDto", locationDto);
        log.info("locationDto: {}, {}", locationDto.getLat(), locationDto.getLnt());
        model.addAttribute(list);
        return "customer/store"; // todo
    }

    /**
     * customer에게 매장 상세 정보 보여주기
     */
    @GetMapping("/customer/stores/{storeId}")
    public String getStoreDetail(@PathVariable Long storeId, Model model) {
        StoreDto storeDto = storeApiClient.getStoreDetail(storeId);
        model.addAttribute(storeDto);
        return "customer/detail";
    }

    /**
     * manager 매장 등록하기
     */
    @PostMapping("/manager/stores")
    public String registerManagerStore (AddStoreForm form) {
        Long storeId = storeApiClient.registerStore(form);
        managerService.saveStoreId(storeId,managerService.getManagerId());
        return "redirect:/manager";
    }

    /**
     * 매니저가 자신의 매장 + 리뷰 확인하기
     */
    @GetMapping("/manager/store/{managerId}")
    public String getManagerStoreDetail(@PathVariable Long managerId, Model model) {
        Long storeId = managerService.getStoreIdFromManagerId(managerId);
        StoreDto storeDto = storeApiClient.getStoreDetail(storeId);
        model.addAttribute(managerId);
        model.addAttribute(storeDto);
        return "manager/store-detail";
    }
}
