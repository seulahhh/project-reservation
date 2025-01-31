package com.project.reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.project.global", "com.project.reservation", "com.project.infrastructure"})
public class ReservationApplication {
    public static void main (String[] args) {
        SpringApplication.run(com.project.reservation.ReservationApplication.class, args);
    }
}