package com.project.member.web;

import com.project.member.service.RegistrationService;
import com.project.member.model.dto.form.SignupForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class SignUp {
    private final RegistrationService registrationService;

    @PostMapping("/register")
    public String register (
            @ModelAttribute SignupForm signupForm) {
        if (signupForm.getRole()
                      .equals("ROLE_MANAGER")) {
            registrationService.registerManager(signupForm);
        } else {
            registrationService.registerCustomer(signupForm);
        }
        return "main";
    }
}
