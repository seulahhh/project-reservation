package com.project.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.project.global", "com.project.store", "com.project.infrastructure"})
public class StoreServiceApplication {
    public static void main (String[] args) {
        SpringApplication.run(com.project.store.StoreServiceApplication.class, args);
    }
}
