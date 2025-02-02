package com.project.reservation.util;

import com.project.global.dto.ReservationDto;
import com.project.global.dto.ReviewDto;
import com.project.global.dto.StoreDto;
import com.project.global.dto.form.CreateReservationForm;
import com.project.reservation.exception.CustomException;
import com.project.reservation.exception.ErrorCode;
import com.project.reservation.persistence.entity.Reservation;
import com.project.reservation.persistence.entity.Review;
import com.project.reservation.persistence.entity.Store;
import com.project.reservation.persistence.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class DtoMapper {
    private final StoreRepository storeRepository;

    public CreateReservationForm toCreateReservationDto (
            Reservation reservation) {
        return CreateReservationForm.builder()
                .reservationTime(reservation.getReservationTime())
                .customerId(reservation.getCustomerId())
                .storeId(reservation.getStore().getId())
                .guestCount(reservation.getGuestCount())
                .build();
    }

    public Reservation toEntity (CreateReservationForm form) {
        Store store = storeRepository.findById(form.getStoreId())
                                              .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));
        return Reservation.builder()
                .customerId(form.getCustomerId())
                .store(store)
                .reservationTime(form.getReservationTime())
                .guestCount(form.getGuestCount())
                .build();
    }

    public ReservationDto toDto (Reservation reservation) {
        return ReservationDto.builder()
                .id(reservation.getId())
                .reservationTime(reservation.getReservationTime())
                .customerId(reservation.getCustomerId())
                .storeId(reservation.getStore().getId())
                .guestCount(reservation.getGuestCount())
                .isConfirmed(reservation.isConfirmed())
                .hasArrived(reservation.isHasArrived())
                .reservationStatus(reservation.getReservationStatus())
                .build();

    }
        public StoreDto toStoreDto (Store store){
            return StoreDto.builder()
                    .id(store.getId())
                    .name(store.getName())
                    .reviews(store.getReviews()
                                  .stream()
                                  .map(this::toReviewDto)
                                  .toList())
                    .number(store.getNumber())
                    .lat(store.getLat())
                    .lnt(store.getLnt())
                    .rating(store.getRating())
                    .build();
        }

        public ReviewDto toReviewDto (Review review){
            return ReviewDto.builder()
                    .customerId(review.getCustomerId())
                    .id(review.getId())
                    .storeId(review.getStore()
                                   .getId())
                    .title(review.getTitle())
                    .body(review.getBody())
                    .rating(review.getRating())
                    .build();
        }
    }

