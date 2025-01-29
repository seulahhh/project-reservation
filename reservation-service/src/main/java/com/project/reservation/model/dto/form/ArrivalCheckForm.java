package com.project.reservation.model.dto.form;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArrivalCheckForm {
    private LocalDateTime checkAt;
    private Long reservationId;
    private Long customerId;
    private Long storeId;
}
