package com.project.member.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Order(1)
    @Bean
    public SecurityFilterChain customerFilterChain (
            HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
//                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .securityMatcher("/customer/**", "/reservation/**") // 이 필터체인은 /customer/login
                // 에만 적용됨
                .authorizeHttpRequests(auth ->
                                               auth.requestMatchers(
                                                       "/customer/login","/static/css/**", "/static/js/**",
                                                       "/logout")
                                                   .permitAll()
                                                   .anyRequest()
                                                   .hasRole("CUSTOMER"))
                .formLogin(login -> {
                    login.loginPage("/customer/login") // post url
                            .successHandler(customAuthenticationSuccessHandler)
                         // 후 기본 리다이렉션 경로1
                         .failureUrl("/customer/login"); // 로그인 실패 시 경로
                })
                .httpBasic(httpBasic -> httpBasic.realmName("myapp"));

        return http.build();
    }

    @Order(2)
    @Bean
    public SecurityFilterChain managerFilterChain (
            HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
//                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .securityMatcher("/manager/**") // 이 필터체인은 /customer/login 에만
                // 적용됨
                .authorizeHttpRequests(auth ->
                                               auth.requestMatchers("/manager/login", "/logout" ,"/css/**", "/js/**")
                                                   .permitAll()
                                                   .anyRequest()
                                                   .hasRole("MANAGER"))
                .formLogin(login -> {
                    login.loginPage("/manager/login")
                         .successHandler(customAuthenticationSuccessHandler)

                         // 후 기본 리다이렉션 경로
                         
                         .failureUrl("/manager/login"); // 로그인 실패 시 경로
                })
                .httpBasic(httpBasic -> httpBasic.realmName("myapp"));


        return http.build();
    }

    @Order(3)
    @Bean
    public SecurityFilterChain securityFilterChain (
            HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
//                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/css/**", "/js/**")
                        .permitAll()
                        .anyRequest()
                        .permitAll()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(logout -> {
                            logout.logoutSuccessUrl("/")
                                  .logoutUrl("/logout")
                                  .invalidateHttpSession(true);
                        }
                )
                .httpBasic(httpBasic -> httpBasic.realmName("myapp"));

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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:8080");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}
