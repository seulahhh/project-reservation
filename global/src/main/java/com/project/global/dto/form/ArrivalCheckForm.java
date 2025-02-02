package com.project.global.dto.form;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ArrivalCheckForm {
    private LocalDateTime checkAt;
    private Long reservationId;
    private Long customerId;
    private Long storeId;
}
