package com.project.member.persistence.entity;

import com.project.member.model.dto.CustomerDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @Builder.Default
    private boolean enabled = true;

    private String name;

    private String phone;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        return Collections.singleton(() -> "ROLE_CUSTOMER");
    }

    @Override
    public String getUsername () {
        return email;
    }

    public static Customer from(CustomerDto dto) {
        return Customer.builder()
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .password(dto.getPassword())
                .name(dto.getName())
                .build();
    }
}
