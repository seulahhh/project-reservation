package com.project.reservation.persistence.repository.reservation;

import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ReservationRepositoryCustom {
    Optional<Long> findCustomerIdByReservationId (Long reservationId);
}
