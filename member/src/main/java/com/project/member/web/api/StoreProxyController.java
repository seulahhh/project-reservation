package com.project.member.web.api;

import com.project.global.dto.LocationDto;
import com.project.global.dto.StoreDto;
import com.project.global.dto.form.AddStoreForm;
import com.project.member.client.StoreApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StoreProxyController {
    private final StoreApiClient storeApiClient;

    /**
     * 조건에 맞게 정렬하여 매장 리스트 보여주기
     */
    @GetMapping("/customers/stores")
    public String getStoreListOrderByDistance (
            @RequestParam String sortBy, @RequestParam Double lat,
            @RequestParam Double lnt, Model model
    ) {
        List<StoreDto> list;
        LocationDto location = LocationDto.of(lat, lnt);
        if (sortBy.equals("distance")) {
            list = storeApiClient.getStoreListOrderByDistance(location);
        } else if (sortBy.equals("name")) {
            list = storeApiClient.getStoreListOrderByName(location);
        } else {
            list = storeApiClient.getStoreListOrderByRating(location);
        }
        model.addAttribute(list);
        return "customer/store"; // todo
    }

    /**
     * customer에게 매장 상세 정보 보여주기
     */
    @GetMapping("/customer/stores/{storeId}")
    public String getStoreDetail(@PathVariable Long storeId, Model model) {
        StoreDto storeDetail = storeApiClient.getStoreDetail(storeId);
        model.addAttribute(storeDetail);
        return "customer/detail";
    }

    /**
     * manager 매장 등록하기
     */
    @PostMapping("/manager/stores")
    public String getStoreDetail(AddStoreForm form) {
        storeApiClient.registerStore(form);

        return "redirect:/manager/store";
    }
}
