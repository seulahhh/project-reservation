package com.project.reservation.persistence.repository.reservation;

import com.project.reservation.persistence.entity.QReservation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    QReservation qRsv = QReservation.reservation;

    @Override
    public Optional<Long> findCustomerIdByReservationId (Long reservationId) {
        return Optional.ofNullable(queryFactory.select(qRsv.customerId)
                                               .from(qRsv)
                                               .where(qRsv.id.eq(reservationId))
                                               .fetchOne());
    }

    @Override
    public Long findAvailableDateTime (Long storeId, LocalDateTime startTime,
            LocalDateTime endTime) {
        return 0L;
    }

}
