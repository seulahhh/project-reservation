package com.project.member.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequiredArgsConstructor
public class Login {

    // 기본 homepage
    @GetMapping("/home")
    public String homePage () {
        return "home";
    }


    @GetMapping("/")
    public String home1Page () {
        return "home";
    }



    // customer login 페이지
    @GetMapping("/customer/login")
    public ModelAndView customerLogin(HttpSession session, ModelAndView mv) {
        mv.addObject("isCustomer", true);
        mv.setViewName("login");
        return mv;
    }


    // customer home
    @GetMapping("/customer/home")
    public ModelAndView customerHome() {
        ModelAndView mv = new ModelAndView("customer-home");
        SecurityContext context = SecurityContextHolder.getContext();
        String customerName = context.getAuthentication().getName();
        mv.addObject("customerName", customerName);
        return mv;
    }

    // manager login 페이지
    @GetMapping("/manager/login")
    public ModelAndView managerLogin(HttpSession session, ModelAndView mv) {
        mv.addObject("isManager", true);
        mv.setViewName("login");
        return mv;
    }

    // manager home
    @GetMapping("/manager/home")
    public ModelAndView managerHome() {
        ModelAndView mv = new ModelAndView("manager-home");
        SecurityContext context = SecurityContextHolder.getContext();
        String managerName = context.getAuthentication().getName();
        mv.addObject("managerName", managerName);
        return mv;
    }

    // TODO 임시 로직
    @GetMapping("/getAuth")
    @ResponseBody
    public ResponseEntity<String> getAuth () {
        String author = SecurityContextHolder.getContext()
                                             .getAuthentication()
                                             .getAuthorities()
                                             .stream()
                                             .map(GrantedAuthority::getAuthority)
                                             .findFirst()
                                             .orElse("null");
        return ResponseEntity.ok(author);
    }



}
