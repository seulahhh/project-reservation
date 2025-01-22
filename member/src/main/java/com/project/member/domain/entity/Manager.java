package com.project.member.domain.entity;

import com.project.member.model.dto.CustomerDto;
import com.project.member.model.dto.ManagerDto;
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
public class Manager extends BaseEntity implements UserDetails  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;
    private boolean enabled = true;

    private String name;
    private String phone;

    @OneToOne(mappedBy = "manager")
    private Store store;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        return Collections.singleton(() -> "ROLE_MANAGER");
    }

    @Override
    public String getUsername () {
        return email;
    }

}
