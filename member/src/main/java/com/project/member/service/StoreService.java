package com.project.member.service;

import com.project.member.domain.entity.Manager;
import com.project.member.domain.entity.Review;
import com.project.member.domain.entity.Store;
import com.project.member.domain.repository.ManagerRepository;
import com.project.member.domain.repository.ReviewRepository;
import com.project.member.domain.repository.StoreRepository;
import com.project.member.model.dto.ReviewDto;
import com.project.member.model.dto.form.AddStoreForm;
import com.project.member.model.enums.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.project.member.model.enums.Message.*;

@Service
@RequiredArgsConstructor

public class StoreService {
    private final StoreRepository storeRepository;
    private final ManagerRepository managerRepository;
    private final ReviewRepository reviewRepository;

// ----------------- 메인 서비스 로직 시작
    // manager
    // 매장 등록하기
    @Transactional
    public Message addStore (AddStoreForm form) {
        Manager manager = getManagerFromId(form.getManagerId());

        Store store = Store.builder()
                .name(form.getName())
                .manager(manager)
                .number(form.getNumber())
                .lat(form.getLat())
                .lnt(form.getLnt())
                .build();
        storeRepository.save(store);

        return ADD_COMPLETE;
    }

    // 매장 삭제하기
    @Transactional
    public Message deleteStore (Long storeId) {
        Store store = storeRepository.findById(storeId)
                                     .orElseThrow(() ->
                                                          new RuntimeException("해당 매장을 찾을 수 없습니다")); // todo custom exception
        storeRepository.delete(store);

        return DELETE_COMPLETE;
    }

    // 매장 수정하기
    // todo 추후 수정 기능 필요시 추가

    // 내 매장 리뷰 보기
    public List<ReviewDto> showMyStoreReviews (Long managerId) {
        List<Review> myStoreReviews = getMyStoreReviews(managerId);
        return myStoreReviews.stream()
                                             .map(ReviewDto::from)
                                             .toList();
        // todo List 보여주는 로직
    }

    // 내 매장 리뷰 삭제하기
    // todo 공통로직으로 사용 고려
    @Transactional
    public void deleteReview (Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new RuntimeException("리뷰 삭제 에러")); // todo exception처리 수정필요
        reviewRepository.delete(review);
    }
// ----------------- 메인 서비스 로직 끝

    // 내매장 리뷰 가져오기
    public List<Review> getMyStoreReviews (Long managerId) {
        Store store = storeRepository.findByManager_Id(managerId).orElseThrow(() ->
                new RuntimeException("해당 매장이 없습니다"));

        return store.getReviews();
    }

    // store manager id 받아와서 entity로 반환
    @Transactional
    public Manager getManagerFromId (Long managerId) {
        return managerRepository.findById(managerId)
                                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다")); // todo customeException
    }
    // customer
    // 매장 조회하기
}
