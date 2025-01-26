package com.project.member.service;

import com.project.member.model.dto.LoginForm;
import com.project.member.model.dto.ReviewDto;
import com.project.member.model.types.Message;
import com.project.member.persistence.entity.Customer;
import com.project.member.persistence.entity.QReview;
import com.project.member.persistence.entity.Review;
import com.project.member.persistence.entity.Store;
import com.project.member.persistence.repository.CustomerRepository;
import com.project.member.persistence.repository.ReviewRepository;
import com.project.member.persistence.repository.StoreRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.project.member.model.types.Message.*;

/**
 * distance, searching...
 */
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final JPAQueryFactory queryFactory;
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final StoreService storeService;
    QReview qReview = QReview.review;
    // repository에서 customer 찾기

    /**
     * 사용X
     */
    public String getCustomer (LoginForm form) {
        Customer customer =
                customerRepository.findByEmailAndPassword(form.getEmail(),
                                                          form.getPassword())
                                  .orElseThrow(() ->
                                                       new RuntimeException(
                                                               "사용자를 찾을 수 없습니다") // TODO customException 처리
                                  );
        return form.getRole();
    }

    /**
     * 리뷰 저장하기 (등록하기)
     */
    @Transactional
    public Message writeReview (ReviewDto dto) {
        Store store = storeRepository.findById(dto.getStoreId())
                                     .orElseThrow(); // todo Custom Exception

        Review newReview = Review.builder()
                .title(dto.getTitle())
                .body(dto.getBody())
                .rating(dto.getRating())
                .customerId(dto.getCustomerId())
                .build();

        store.addReview(newReview); // store-> 영속상태, DB자동반영 O
        storeService.updateStoreRating(dto.getStoreId());

        return ADD_COMPLETE;
    }

    /**
     * 리뷰 수정하기
     */
    @Transactional
    public Message updateReview (ReviewDto dto) {
        Review review = getReview(dto.getId(), dto.getCustomerId(),
                                  dto.getStoreId());
        review.setTitle(dto.getTitle());
        review.setBody(dto.getBody());
        review.setRating(dto.getRating());
        reviewRepository.save(review);
        storeService.updateStoreRating(dto.getStoreId());

        return UPDATE_COMPLETE;
    }

    /**
     * 리뷰 삭제하기
     */
    @Transactional
    public Message deleteReview (ReviewDto dto) {
        Review review = getReview(dto.getId(), dto.getCustomerId(),
                                  dto.getStoreId());
        reviewRepository.delete(review);
        storeService.updateStoreRating(dto.getStoreId());

        return DELETE_COMPLETE;
    }

    /**
     * 내 위치 가져오기
     * @return
     * LocationDto Cusotmer location
     */

    /**
     * 리뷰 가져오기 (내가 선택한 특정 단일 리뷰)
     * - 검증 포함
     */
    public Review getReview (Long reviewId, Long customerId, Long storeId) {
        Review review = reviewRepository.findById(reviewId)
                                        .orElseThrow(); // todo

        // 해당 리뷰가 맞는 가게의 맞는 고객이 작성한 리뷰인지 먼저 확인
        if (!Objects.equals(reviewId, customerId)
                || !Objects.equals(reviewId, storeId)) {
            throw new RuntimeException("not found target review"); // todo
            // Custome Exception
        }
        return review;
    }

    /**
     * 리뷰 가져오기 (customer 가 작성한 모든 리뷰)
     * (아직 사용 사항 없음)
     */
//    public List<Review> getMyReviews (Long customerId) {
//        QReview review = QReview.review;
//        return queryFactory.selectFrom(review)
//                       w    .where(review.customer.id.eq(customerId))
//                           .fetch();
//    }



    /**
     * 리뷰 가져오기 (내가 선택한 특정 매장의 리뷰)
     */
    public List<Review> getReviews (Long storeId) {
        QReview review = QReview.review;
         return queryFactory.selectFrom(review)
                                     .where(review.store.id.eq(storeId))
                                     .fetch();
    }
    /**
     * validation
     */

}
