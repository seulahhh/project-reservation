package com.project.member.model.dto.form;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupForm {
    private String email;
    private String password;
    private boolean enabled;
    private String role;
    private String name;
    private String phone;
}
