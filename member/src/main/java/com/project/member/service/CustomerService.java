package com.project.member.service;

import com.project.global.dto.ReservationDto;
import com.project.member.exception.CustomException;
import com.project.member.model.dto.CustomerDto;
import com.project.member.model.dto.LoginForm;
import com.project.member.model.dto.form.CreateReservationForm;
import com.project.member.model.types.Message;
import com.project.member.persistence.entity.Customer;
import com.project.member.persistence.repository.CustomerRepository;
import com.project.member.util.mapper.CustomerMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


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
    private final WebClient webClient;
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


//    /**
//     * 리뷰 가져오기 (내가 선택한 특정 매장의 리뷰)
//     */
//    public List<Review> getReviews (Long storeId) {
//        QReview review = QReview.review;
//         return queryFactory.selectFrom(review)
//                                     .where(review.store.id.eq(storeId))
//                                     .fetch();
//    }

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
