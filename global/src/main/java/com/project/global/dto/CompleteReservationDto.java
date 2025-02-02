package com.project.global.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompleteReservationDto {
    private String storeName;
    private LocalDateTime reservationTime;
    private Long guestCount;
}
