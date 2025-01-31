package com.project.member.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
    Long id;
    String email;
    String password;
    String name;
    String phone;
}
