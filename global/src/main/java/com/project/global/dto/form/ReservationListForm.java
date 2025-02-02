package com.project.global.dto.form;

import com.project.global.dto.ReservationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationListForm {
    private Long id;
    private String customerName;
    private String customerEmail;
    private LocalDateTime reservationTime;
    private Long guestCount;
    private ReservationStatus status;
}
