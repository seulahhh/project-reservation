package com.project.member.model.dto;

import com.project.member.model.enums.Role;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {
    private String email;
    private String password;
    private String role;
}
