package com.project.reservation.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateReservationForm {
    private LocalDateTime reservationTime;
    private Long customerId;
    private Long storeId;
    private Long guestCount;
}
