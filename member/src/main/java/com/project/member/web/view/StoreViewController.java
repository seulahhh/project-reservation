package com.project.member.web.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StoreViewController {

    /**
     * Customer - 매장 페이지 진입
     */
    @GetMapping("/customer/stores")
    public String customerStoresPage (Model model) {
        return "customer/store";
    }

    /**
     * Manager - 내 매장 보기
     */
    @GetMapping("/manager/store")
    public String managerStorePage () {
        return "manager/store";
    }
}
