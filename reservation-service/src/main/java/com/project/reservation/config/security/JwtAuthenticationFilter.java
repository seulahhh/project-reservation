package com.project.reservation.config.security;

import com.project.global.config.JwtTokenService;
import com.project.global.util.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal (HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        log.info("is validated token : {}", jwtTokenProvider.validateToken(token));
        if (token != null && jwtTokenProvider.validateToken(token)) {
            log.info("검증 완료 후 SecurityContext 세팅 시작");
            String username = jwtTokenProvider.getUsername(token);
            String role = jwtTokenProvider.getUserRole(token);
            System.out.println("role = " + role);
            System.out.println("username = " + username);
            GrantedAuthority authority = new SimpleGrantedAuthority(role);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(username, null,
                                                           Collections.singletonList(authority));
            SecurityContext context = SecurityContextHolder.getContext();
            context
                                 .setAuthentication(authentication);
            log.info("{} --> Context setting 끝", SecurityContextHolder.getContext().getAuthentication());
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken (HttpServletRequest request) {
        String token = null;
        String key = request.getHeader("AUTH-CODE");

        if (key == null || key.isEmpty()) {
            log.error("not match authentication");
        } else {
            token = jwtTokenService.getTokenFromRedis(key);
        }
        return token;
    }
}
