package com.project.member.service;

import com.project.member.model.dto.ReviewDto;
import com.project.member.model.dto.form.AddStoreForm;
import com.project.member.model.types.Message;
import com.project.member.persistence.entity.Manager;
import com.project.member.persistence.entity.Review;
import com.project.member.persistence.entity.Store;
import com.project.member.persistence.repository.ManagerRepository;
import com.project.member.persistence.repository.ReviewRepository;
import com.project.member.persistence.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.member.model.types.Message.ADD_COMPLETE;
import static com.project.member.model.types.Message.DELETE_COMPLETE;

@Service
@RequiredArgsConstructor

public class ManagerService {
    private final StoreRepository storeRepository;
    private final ManagerRepository managerRepository;
    private final ReviewRepository reviewRepository;

    // ----------------- 메인 서비스 로직 시작
    // manager

    /**
     * 자신의 매장 등록하기
     */
    @Transactional
    public Message addStore (AddStoreForm form) {
        if (storeRepository.findByManager_Id(form.getManagerId())
                           .isPresent()) {
            throw new RuntimeException("이미 등록한 매장이 있습니다.");
        }
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

    /**
     * 자신의 매장 삭제하기
     */
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

    // 본인 매장 리뷰 보기
    public List<ReviewDto> showMyStoreReviews (Long managerId) {
        List<Review> myStoreReviews = getMyStoreReviews(managerId);
        return myStoreReviews.stream()
                             .map(ReviewDto::from)
                             .toList();
        // todo List 보여주는 로직
    }

    // 본인 매장 리뷰 삭제하기
    // todo 공통로직으로 사용 고려
    @Transactional
    public void deleteReview (Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                                        .orElseThrow(() ->
                                                             new RuntimeException("리뷰 삭제 에러")); // todo exception처리 수정필요
        reviewRepository.delete(review);
        // todo 리뷰삭제 시 store의 rate update
    }

    // ----------------- 메인 서비스 로직 끝

    // 리뷰

    /**
     * 자신의 매장 리뷰 가져오기
     */
    // todo reviewDto로 변환 후 반환 필요
    public List<Review> getMyStoreReviews (Long managerId) {
        Store store = storeRepository.findByManager_Id(managerId)
                                     .orElseThrow(() ->
                                                          new RuntimeException("해당 매장이 없습니다"));

        return store.getReviews();
    }

    /**
     * Manager Id로 Manager(entity) 가져오기
     */
    public Manager getManagerFromId (Long managerId) {
        return managerRepository.findById(managerId)
                                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다")); // todo customeException
    }
}
