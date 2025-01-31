package com.project.reservation.util;

import com.project.global.dto.ReservationDto;
import com.project.global.dto.form.CreateReservationForm;
import com.project.reservation.persistence.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationMapper {

    public CreateReservationForm toCreateReservationDto (Reservation reservation) {
        return CreateReservationForm.builder()
                .reservationTime(reservation.getReservationTime())
                .customerId(reservation.getCustomerId())
                .storeId(reservation.getStoreId())
                .guestCount(reservation.getGuestCount())
                .build();
    }
    public Reservation toEntity(CreateReservationForm form) {
        return Reservation.builder()
                .customerId(form.getCustomerId())
                .storeId(form.getStoreId())
                .reservationTime(form.getReservationTime())
                .guestCount(form.getGuestCount())
                .build();
    }

    public ReservationDto toDto (Reservation reservation) {
        return ReservationDto.builder()
                .id(reservation.getId())
                .reservationTime(reservation.getReservationTime())
                .customerId(reservation.getCustomerId())
                .storeId(reservation.getStoreId())
                .guestCount(reservation.getGuestCount())
                .isConfirmed(reservation.isConfirmed())
                .hasArrived(reservation.isHasArrived())
                .reservationStatus(reservation.getReservationStatus())
                .build();
    }
}
