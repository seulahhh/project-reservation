package com.project.infrastructure.kafka.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ReservationDto {
    private Long id;
    private LocalDateTime reservationTime;
    private Long customerId;
    private Long storeId;
    private Long guestCount;
    private boolean isConfirmed;
    private boolean hasArrived;
    private String reservationStatus;
}

