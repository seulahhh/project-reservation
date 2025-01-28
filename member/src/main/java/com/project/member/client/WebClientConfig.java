package com.project.member.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${api.base.url}")
    private String apiBaseUrl; // todo 테스트단계


    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl(apiBaseUrl).build();
    }
}
