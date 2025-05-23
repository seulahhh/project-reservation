package com.project.reservation.service;

import com.project.global.dto.form.CreateReviewForm;
import com.project.reservation.exception.CustomException;
import com.project.reservation.persistence.entity.Review;
import com.project.reservation.persistence.entity.Store;
import com.project.reservation.persistence.repository.reservation.ReservationRepository;
import com.project.reservation.persistence.repository.review.ReviewRepository;
import com.project.reservation.persistence.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.project.reservation.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final StoreRepository storeRepository;
    private final StoreService storeService;
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;

    /**
     * 리뷰 작성하기
     */
    @Transactional
    public boolean createReview (CreateReviewForm form) {
        Store store = storeRepository.findById(form.getStoreId())
                                     .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        Long customerId =
                reservationRepository.findCustomerIdByReservationId(form.getReservationId())
                                               .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));
        if (form.getCustomerId() != customerId) {
            throw new CustomException(REVIEW_WRITE_PERMISSION_DENIED);
        }

        Review newReview = Review.builder()
                .title(form.getTitle())
                .body(form.getBody())
                .rating(form.getRating())
                .customerId(form.getCustomerId())
                .store(store)
                .build();

        store.addReview(newReview); // store-> 영속상태, DB자동반영 O
        storeService.updateStoreRating(form.getStoreId());

        return true;
    }

    /**
     * 리뷰 수정하기
     */
    @Transactional
    public boolean updateReview (CreateReviewForm dto) {
        Review review = reviewRepository.findById(dto.getId())
                                        .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));
        review.setTitle(dto.getTitle());

        review.setTitle(dto.getTitle());
        review.setBody(dto.getBody());
        review.setRating(dto.getRating());
        reviewRepository.save(review);
        storeService.updateStoreRating(dto.getStoreId());
        return true;
    }

    /**
     * 리뷰삭제하기 (manager)
     * Exception O
     */
    @Transactional
    public boolean deleteReviewByManager (Long reviewId, Long managerId) {
        Store storeByReviewId = reviewRepository.findStoreByReviewId(reviewId)
                                                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));
        Store storeByManagerId = storeRepository.findStoreByManagerId(managerId)
                                                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));
        if (storeByReviewId.getId() != storeByManagerId.getId()) {
            throw new CustomException(REVIEW_DELETE_PERMISSION_DENIED);
        }
        reviewRepository.deleteById(reviewId);
        storeService.updateStoreRating(storeByManagerId.getId());
        return true;
    }

    /**
     * 리뷰삭제하기 (customer)
     * Exception O
     */
    @Transactional
    public boolean deleteReviewByCustomer (Long reviewId, Long customerId) {
        Long customerIdByReviewId =
                reviewRepository.findCustomerIdByReviewId(reviewId)
                                                    .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));
        if (!Objects.equals(customerId, customerIdByReviewId)) {
            throw new CustomException(REVIEW_DELETE_PERMISSION_DENIED);
        }
        Store store = reviewRepository.findStoreByReviewId(reviewId)
                                      .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));
        reviewRepository.deleteById(reviewId);
        storeService.updateStoreRating(store.getId());
        return true;
    }
}
