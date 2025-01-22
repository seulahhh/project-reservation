package com.project.member.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain customerFilterChain (
            HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .securityMatcher("/customer/**") // 이 필터체인은 /customer/login
                // 에만 적용됨
                .authorizeHttpRequests(auth ->
                                               auth.requestMatchers(
                                                       "/customer/login",
                                                       "/logout")
                                                   .permitAll()
                                                   .anyRequest()
                                                   .hasRole("CUSTOMER"))
                .formLogin(login -> {
                    login.loginPage("/customer/login") // post url
                         .failureUrl("/customer/login")
                         .defaultSuccessUrl("/customer/home", true)// 로그인 성공
                         // 후 기본 리다이렉션 경로
                         .failureUrl("/customer/login"); // 로그인 실패 시 경로
                });
        return http.build();
    }

    @Bean
    public SecurityFilterChain managerFilterChain (
            HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .securityMatcher("/manager/**") // 이 필터체인은 /customer/login 에만
                // 적용됨
                .authorizeHttpRequests(auth ->
                                               auth.requestMatchers("/manager/login", "/logout")
                                                   .permitAll()
                                                   .anyRequest()
                                                   .hasRole("MANAGER"))
                .formLogin(login -> {
                    login.loginPage("/manager/login")
                         .defaultSuccessUrl("/manager/home", true) // 로그인 성공
                         // 후 기본 리다이렉션 경로
                         .failureUrl("/manager/login"); // 로그인 실패 시 경로
                });

        return http.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain (
            HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        .anyRequest()
                        .permitAll()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(logout -> {
                            logout.logoutSuccessUrl("/")
                                  .logoutUrl("/logout")
                                  .invalidateHttpSession(true)
                                  .clearAuthentication(true);
                        }
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager (
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
