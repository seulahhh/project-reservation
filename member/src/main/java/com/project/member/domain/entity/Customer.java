package com.project.member.domain.entity;

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
public class Customer implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;
    private boolean enabled;

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
}
