package com.project.member.web.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterViewController {
    /**
     * 회원가입 페이지
     */
    @GetMapping
    public String registerPage() {

        return "register";
    }
}
