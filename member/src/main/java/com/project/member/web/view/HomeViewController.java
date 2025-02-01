package com.project.member.web.view;

import com.project.member.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeViewController {
    private final ManagerService managerService;

    /**
     * 메인페이지
     */
    @GetMapping("/home")
    public String homePage () {
        return "main";
    }

    /**
     * 메인페이지
     */
    @GetMapping("/")
    public String defaultPage () {
        return "main";
    }

    /**
     * customer login 페이지
     * - view 페이지는 하나로 통일하고 경로에 따라 다른 ui 노출
     */
    @GetMapping("/customer/login")
    public String customerLogin(Model model) {
        model.addAttribute("isCustomer", true);
        return "login";
    }

    /**
     * manager login 페이지
     * - view 페이지는 하나로 통일하고 경로에 따라 다른 ui 노출
     */
    @GetMapping("/manager/login")
    public String managerLogin(Model model) {
        model.addAttribute("isManager", true);
        return "login";
    }

    /**
     * Customer Home (로그인 후)
     */
    @GetMapping("/customer")
    public String customerHome() {
        return "customer/customerHome";
    }

    /**
     * Manager Home (로그인 후)
     */
    @GetMapping("/manager")
    public String managerHome (Model model) {
        model.addAttribute("hasStore", managerService.hasRegistered());
        model.addAttribute("managerId", managerService.getManagerId());
        // todo 가진 매장이 있는지 확인하는 로직
        return "manager/managerHome";
    }
}
