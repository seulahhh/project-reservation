package com.project.reservation.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger springdoc-ui 구성파일
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info().title("Reservation Project")
                              .version("0.0.1")
                              .description("예약 시스템 프로젝트");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}

