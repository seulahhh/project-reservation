package com.project.member.auth.config;

import com.project.global.config.JwtTokenService;
import com.project.global.util.JwtTokenProvider;
import com.project.member.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenService jwtTokenService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 로그인 성공 후 처리할 로직 추가
        String email = authentication.getName();
        String role = authentication.getAuthorities()
                                               .stream()
                                               .map(GrantedAuthority::getAuthority)
                                               .findFirst().get();
        String token = jwtTokenProvider.generateToken(email, role);
        jwtTokenService.storeToken(email, token);
        log.info("success Handler end complete! with store token: {}", token);

        if (role.contains("MANAGER")) {
            response.sendRedirect("/manager");
        } else if (role.contains("CUSTOMER")) {
            response.sendRedirect("/customer");
        }
    }
}
