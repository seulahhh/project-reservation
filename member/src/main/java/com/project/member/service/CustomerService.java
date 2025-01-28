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
import org.springframework.web.bind.annotation.PostMapping;

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
     * 리뷰 작성하기
     */
    @Transactional
    public Message createReview (ReviewDto dto) {
        // 예약 내역 확인 후 리뷰 작성 가능
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
     * 리뷰 가져오기 (내가 선택한 특정 매장의 리뷰)
     */
    public List<Review> getReviews (Long storeId) {
        QReview review = QReview.review;
         return queryFactory.selectFrom(review)
                                     .where(review.store.id.eq(storeId))
                                     .fetch();
    }

    /**
     * 예약하기
     */

//    @PostMapping("/customer/login")
//    public String loginRequest (LoginRequest loginRequest, Model model) {
//        loginRequest.setUserRole("ROLE_CUSTOMER");
//        System.out.println(loginRequest.getUsername());
//        System.out.println(loginRequest.getPassword());
//        String res = webClient.post()
//                              .uri("/login/11")
//                              .header("Content-Type", "application/json")
//                              .bodyValue(loginRequest)  // 로그인 요청에 필요한 body 값
//                              .retrieve()
//                              .bodyToMono(String.class)  // 응답을 JWT 토큰으로 받아옴
//                              //                                .onErrorResume(e -> Mono.just("Error"))
//                              .block();// 에러 처리
//        System.out.println(res);
//        model.addAttribute("responses", res);
//        return "after-login-test";
//    }
}
