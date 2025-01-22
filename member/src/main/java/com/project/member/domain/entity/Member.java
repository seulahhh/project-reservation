package com.project.member.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 사용 X
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {

        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword () {
        return this.password;
    }

    @Override
    public String getUsername () {
        return this.email;
    }
}
