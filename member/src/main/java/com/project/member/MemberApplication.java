package com.project.member;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class MemberApplication {

    public static void main (String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }

}