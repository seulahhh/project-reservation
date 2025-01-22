package com.project.member.controller;

import com.project.member.auth.service.RegistrationService;
import com.project.member.model.dto.SignupForm;
import com.project.member.service.CustomerService;
import com.project.member.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping
@RequiredArgsConstructor

public class SignUp {
    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<String> register (
            @RequestBody SignupForm signupForm) {

        if (signupForm.getRole()
                      .equals("ROLE_MANAGER")) {
            registrationService.registerManager(signupForm);
        } else {
            registrationService.registerCustomer(signupForm);
        }
        return ResponseEntity.ok("가입완료!");
    }
}
