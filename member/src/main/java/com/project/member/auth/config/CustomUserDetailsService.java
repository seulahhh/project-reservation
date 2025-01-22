package com.project.member.auth.config;

import com.project.member.domain.entity.Customer;
import com.project.member.domain.entity.Manager;
import com.project.member.domain.repository.CustomerRepository;
import com.project.member.domain.repository.ManagerRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final ManagerRepository managerRepository;
    private final HttpServletRequest httpServletRequest;

    @Override
    public UserDetails loadUserByUsername (
            String email) throws UsernameNotFoundException {
        String requestUrl = httpServletRequest.getRequestURI();
        if (requestUrl.startsWith("/customer")) {
            return customerRepository.findByEmail(email)
                                                  .orElseThrow(() ->
            new UsernameNotFoundException("사용자를 찾을 수 없습니다")); // TODO customException 정의
        }
        else if (requestUrl.startsWith("/manager")) {
            return managerRepository.findByEmail(email)
                                    .orElseThrow(() ->
                                                         new UsernameNotFoundException("사용자를 찾을 수 없습니다")); // TODO customException 정의
        } else {
            throw  new UsernameNotFoundException("잘못된 로그인 접근 시도"); // TODO customException 정의
        }
    }
}
