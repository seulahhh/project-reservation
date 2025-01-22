package com.project.member.domain.entity;

import com.project.member.model.dto.ManagerDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
    private boolean enabled = true;

    private String name;

    private String phone;

    private Double lat;
    private Double lnt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        return Collections.singleton(() -> "ROLE_CUSTOMER");
    }

    @Override
    public String getUsername () {
        return email;
    }

    public static Customer from(Customer dto) {
        return Customer.builder()
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .password(dto.getPassword())
                .name(dto.getName())
                .lat(dto.getLat())
                .lnt(dto.getLnt())
                .build();
    }
}
