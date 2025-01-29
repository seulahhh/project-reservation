package com.project.global.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KioskForm {
    Long customerId;
    Long reservationId;
    boolean isArrived;
}
