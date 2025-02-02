package com.project.reservation.persistence.repository.reservation;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReservationRepositoryCustom {
    Optional<Long> findCustomerIdByReservationId (Long reservationId);

    Long findAvailableDateTime (Long storeId, LocalDateTime startTime,
            LocalDateTime endTime);
}