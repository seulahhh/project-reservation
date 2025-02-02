package com.project.reservation.util;

import com.project.global.dto.CompleteReservationDto;
import com.project.global.dto.ReservationDto;
import com.project.global.dto.form.CreateReservationForm;
import com.project.global.dto.ReviewDto;
import com.project.global.dto.StoreDto;
import com.project.reservation.persistence.entity.Reservation;
import com.project.reservation.persistence.entity.Review;
import com.project.reservation.persistence.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DtoMapper {

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

    public StoreDto toStoreDto (Store store) {
        return StoreDto.builder()
                .id(store.getId())
                .name(store.getName())
                .reviews(store.getReviews().stream().map(this::toReviewDto).toList())
                .number(store.getNumber())
                .lat(store.getLat())
                .lnt(store.getLnt())
                .rating(store.getRating())
                .build();
    }

    public ReviewDto toReviewDto(Review review) {
        return ReviewDto.builder()
                .customerId(review.getCustomerId())
                .id(review.getId())
                .storeId(review.getStore().getId())
                .title(review.getTitle())
                .body(review.getBody())
                .rating(review.getRating())
                .build();
    }
}
