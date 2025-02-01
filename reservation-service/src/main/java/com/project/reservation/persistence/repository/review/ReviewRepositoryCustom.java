package com.project.reservation.persistence.repository.review;

import com.project.reservation.persistence.entity.Store;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ReviewRepositoryCustom {
    Optional<Store> findStoreByReviewId (Long reviewId);
    Optional<Long> findCustomerIdByReviewId (Long reviewId);
}
