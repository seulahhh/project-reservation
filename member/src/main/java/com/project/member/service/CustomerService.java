package com.project.member.service;

import com.project.member.exception.CustomException;
import com.project.member.exception.ErrorCode;
import com.project.member.model.dto.CustomerDto;
import com.project.member.model.dto.LoginForm;
import com.project.member.model.dto.ReservationDto;
import com.project.member.model.dto.ReviewDto;
import com.project.member.model.dto.form.CreateReservationForm;
import com.project.member.model.types.Message;
import com.project.member.persistence.entity.Customer;
import com.project.member.persistence.entity.QReview;
import com.project.member.persistence.entity.Review;
import com.project.member.persistence.entity.Store;
import com.project.member.persistence.repository.CustomerRepository;
import com.project.member.persistence.repository.ReviewRepository;
import com.project.member.persistence.repository.StoreRepository;
import com.project.member.util.mapper.CustomerMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

import static com.project.member.exception.ErrorCode.*;
import static com.project.member.model.types.Message.*;

/**
 * distance, searching...
 */
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerMapper customerMapper;
    private final JPAQueryFactory queryFactory;
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final StoreService storeService;
    private final WebClient webClient;
    QReview qReview = QReview.review;
    // repository에서 customer 찾기

    /**
     * 사용X
     */
    public String getCustomer (LoginForm form) {
        Customer customer =
                customerRepository.findByEmailAndPassword(form.getUsername(),
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
                                     .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

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
                                        .orElseThrow(() -> new CustomException(STORE_NOT_FOUND)); // todo

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
    public ReservationDto createReservation(CreateReservationForm form) {
        System.out.println(form.toString());
        String email = SecurityContextHolder.getContext()
                                           .getAuthentication()
                                           .getName();
        ReservationDto res = webClient.post()
                                      .uri("/api/reservation")
                                      .header("Content-Type", "application/json")
                                      .bodyValue(form)
                                      .retrieve()
                                      .bodyToMono(ReservationDto.class)
                                      .block();
        return res;
    }

    /**
     * 현재 로그인한 사용자의 email을 가져오기
     */
    public String getCurrentUserEmail() {
        return SecurityContextHolder.getContext()
                                    .getAuthentication()
                                    .getName();
    }

    /**
     * 현재 로그인한 customer의 정보로 customerId 가져오기
     */
    public Long getCurrentCustomerId () {
        String email = getCurrentUserEmail();
        Customer customer = customerRepository.findByEmail(email)
                                              .orElseThrow(() -> new CustomException(USER_NOT_FOUND));// todo
        return customer.getId();
    }

    /**
     * 현재 로그인한 customer가져오기 (dto)
     *
     */
    public CustomerDto getCustomerDto() {
        String email = SecurityContextHolder.getContext()
                                           .getAuthentication()
                                           .getName();
        Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        return customerMapper.toDto(customer);
    }
}
