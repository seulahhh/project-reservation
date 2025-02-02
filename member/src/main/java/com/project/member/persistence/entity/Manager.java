package com.project.member.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
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
    @Builder.Default
    private boolean enabled = true;

    private String name;
    private String phone;

    @Column(name = "store_id", unique = true)
    private Long storeId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        return Collections.singleton(() -> "ROLE_MANAGER");
    }

    @Override
    public String getUsername () {
        return email;
    }

    public void addStoreId(Long storeId) {
        this.storeId = storeId;
    }
}
