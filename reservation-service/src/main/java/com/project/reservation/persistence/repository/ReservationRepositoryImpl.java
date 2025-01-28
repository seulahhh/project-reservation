package com.project.reservation.persistence.repository;

import com.project.reservation.persistence.entity.QReservation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    QReservation qRsv = QReservation.reservation;

}
