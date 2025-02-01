package com.project.member;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@RequiredArgsConstructor
@ComponentScan(basePackages = "com.project")
public class MemberApplication {
    public static void main (String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }

}