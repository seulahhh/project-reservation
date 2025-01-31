package com.project.member.web.view;

import com.project.member.model.dto.LoginForm;
import com.project.member.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView customerLogin(@ModelAttribute LoginForm form, ModelAndView mv) {
        mv.addObject("isCustomer", true);
        mv.setViewName("login");
        return mv;
    }

    /**
     * manager login 페이지
     * - view 페이지는 하나로 통일하고 경로에 따라 다른 ui 노출
     */
    @GetMapping("/manager/login")
    public ModelAndView managerLogin(ModelAndView mv) {
        mv.addObject("isManager", true);
        mv.setViewName("login");
        return mv;
    }

    /**
     * Customer Home (로그인 후)
     */
    @GetMapping("/customer")
    public ModelAndView customerHome() {
        ModelAndView mv = new ModelAndView("customer/customerHome");
        SecurityContext context = SecurityContextHolder.getContext();
        String customerName = context.getAuthentication().getName();
        mv.addObject("name", customerName);
        return mv;
    }

    /**
     * Manager Home (로그인 후)
     */
    @GetMapping("/manager")
    public ModelAndView managerHome() {
        ModelAndView mv = new ModelAndView("manager/managerHome");
        SecurityContext context = SecurityContextHolder.getContext();
        Long managerId = managerService.getManagerId();
        String managerName = context.getAuthentication().getName();
        System.out.println("managerId" + managerId);
        mv.addObject("name", managerName);
        mv.addObject("managerId", managerId);
        return mv;
    }
}
