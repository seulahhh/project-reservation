package com.project.reservation.persistence.entity;

import com.project.reservation.model.types.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static com.project.reservation.model.types.ReservationStatus.PENDING_APPROVAL;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //예약 시간 및 날짜
    private LocalDateTime reservationTime;

    //예약자
    private Long customerId;

    //예약 대상 매장
    private Long storeId;

    //예약인원
    private Long guestCount;

    //해당 예약을 매니저가 수락 했는지
    @Builder.Default
    private boolean isConfirmed = false;

    //키오스크에서 도착 확인을 했는지
    @Builder.Default
    private boolean hasArrived = false;

    //예약상태
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus = PENDING_APPROVAL;
}
