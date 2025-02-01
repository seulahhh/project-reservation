package com.project.reservation.persistence.repository.reservation;

import com.project.reservation.persistence.entity.QReservation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    QReservation qRsv = QReservation.reservation;

    @Override
    public Optional<Long> findCustomerIdByReservationId (Long reservationId) {
        return Optional.ofNullable(queryFactory.select(qRsv.customerId)
                                               .from(qRsv)
                                               .where(qRsv.id.eq(reservationId))
                                               .fetchOne());
    }

}
