package com.project.reservation.persistence.entity;

import com.project.global.dto.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static com.project.global.dto.ReservationStatus.PENDING_APPROVAL;

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

    private LocalDateTime reservationTime;

    private Long customerId;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    private Long guestCount;

    @Builder.Default
    private boolean isConfirmed = false;

    @Builder.Default
    private boolean hasArrived = false;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus = PENDING_APPROVAL;
}
