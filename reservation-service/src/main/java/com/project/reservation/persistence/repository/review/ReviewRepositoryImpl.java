package com.project.reservation.persistence.repository.review;

import com.project.reservation.persistence.entity.QReview;
import com.project.reservation.persistence.entity.Store;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QReview qReview = QReview.review;

    @Override
    public Optional<Store> findStoreByReviewId (Long reviewId) {
        return Optional.ofNullable(queryFactory.select(qReview.store)
                                               .from(qReview)
                                               .where(qReview.id.eq(reviewId))
                                               .fetchOne());
    }

    @Override
    public Optional<Long> findCustomerIdByReviewId (Long reviewId) {
        return Optional.ofNullable(queryFactory.select(qReview.customerId)
                                               .from(qReview)
                                               .where(qReview.id.eq(reviewId))
                                               .fetchOne());
    }

}
