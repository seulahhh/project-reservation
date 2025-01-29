package com.project.member.model.dto.form;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class CreateReservationForm {
    private LocalDateTime reservationTime;
    private Long customerId;
    private Long storeId;
    private Long guestCount;
}