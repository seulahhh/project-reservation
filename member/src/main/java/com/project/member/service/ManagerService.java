package com.project.member.service;

import com.project.member.exception.CustomException;
import com.project.member.model.dto.StoreDto;
import com.project.member.model.dto.form.AddStoreForm;
import com.project.member.model.types.Message;
import com.project.member.persistence.entity.*;
import com.project.member.persistence.repository.ManagerRepository;
import com.project.member.persistence.repository.ReviewRepository;
import com.project.member.persistence.repository.StoreRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.project.member.exception.ErrorCode.STORE_NOT_FOUND;
import static com.project.member.exception.ErrorCode.USER_HAS_EXISTING_STORE;
import static com.project.member.model.types.Message.ADD_COMPLETE;

@Slf4j
@Service
@RequiredArgsConstructor

public class ManagerService {
    private final StoreRepository storeRepository;
    private final ManagerRepository managerRepository;
    private final ReviewRepository reviewRepository;
    private final JPAQueryFactory queryFactory;
    QManager qManager = QManager.manager;
    // ----------------- 메인 서비스 로직 시작
    // manager

    /**
     * 자신의 매장 등록하기
     * Exception O
     */
    //    @Transactional
    //    public String addStore (AddStoreForm form) {
    //        Long managerId = getManagerId();
    //        if (storeRepository.findByManager_Id(managerId)
    //                           .isPresent()) {
    //            throw new RuntimeException("이미 등록한 매장이 있습니다."); // todo
    //        }
    //        Store store = Store.builder()
    //                .name(form.getName())
    //                .manager(getManagerFromId(managerId))
    //                .number(form.getNumber())
    //                .lat(form.getLat())
    //                .lnt(form.getLnt())
    //                .build();
    //
    //        storeRepository.save(store);
    //        return store.getName();
    //    }

    /**
     * 자신의 매장 등록하기
     * 예외처리 O
     */
    @Transactional
    public Message addStore (AddStoreForm form) {
        Long managerId = getManagerId();
        if (!queryFactory.select(QStore.store)
                         .from(QStore.store)
                         .where(QStore.store.managerId.eq(managerId))
                         .fetch()
                         .isEmpty()) {
            throw new CustomException(USER_HAS_EXISTING_STORE);
        }
        Store store = Store.builder()
                .name(form.getName())
                .managerId(managerId)
                .number(form.getNumber())
                .lat(form.getLat())
                .lnt(form.getLnt())
                .build();

        storeRepository.save(store);
        return ADD_COMPLETE;
    }

    /**
     * 자신의 매장 가져오기
     * - 상세정보 포함, 리뷰 리스트 포함
     * - 리뷰 리스트가 주가 됨
     * Exception O
     */
    //    public StoreDto getManagerStore () {
    //        Long managerId = getManagerId();
    //        Store store = storeRepository.findByManager_Id(managerId)
    //                                     .orElseThrow(() -> new
    //                                     RuntimeException(
    //                                             "매장 정보가 없습니다"));
    //        // todo CustomException
    //        System.out.println(store.getReviews());
    //        return StoreDto.from(store);
    //    }

    /**
     * manager Id로 매니저가 등록한 매장 가져오기 (1:1)
     * 예외처리 O
     */
    public StoreDto getManagerStore () {
        Long managerId = getManagerId();
        Store store = queryFactory.select(QStore.store)
                                  .from(QStore.store)
                                  .where(QStore.store.managerId.eq(managerId))
                                  .fetchOne();
        if (store == null) {
            throw new CustomException(STORE_NOT_FOUND);
        }
        return StoreDto.from(store);
    }

    /**
     * 자신의 매장 삭제하기
     * Exception O
     */
    @Transactional
    public void deleteStore (Long storeId) {
        Store store = storeRepository.findById(storeId)
                                     .orElseThrow(() -> new RuntimeException(
                                             "해당 매장을 찾을 수 없습니다")); // todo
        // custom exception
        storeRepository.delete(store);
    }


    /**
     * 매장 리뷰 삭제하기
     * Exception O
     */
    @Transactional
    public void deleteReview (Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                                        .orElseThrow(() -> new RuntimeException("리뷰가 존재하지 않습니다.")); // todo exception처리 수정필요
        Long loginId = getManagerId();
        if (!Objects.equals(review.getStore()
                                  .getManagerId(), loginId)) {
            // 권한 없으면
            throw new RuntimeException("리뷰를 삭제할 수 있는 권한이 없습니다"); // todo
            // customExcpeton
        }
        reviewRepository.deleteById(review.getId());
        // todo 리뷰삭제 시 store의 rate update
    }

    // ----------------- 메인 서비스 로직 끝

    /**
     * Manager Id로 Manager(entity) 가져오기
     */
    public Manager getManagerFromId (Long managerId) {
        return managerRepository.findById(managerId)
                                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다")); // todo customeException
    }

    /**
     * storeId로 managerId 가져오기
     *
     * @param storeId
     * @return managerId
     */
    public Long getManagerIdFromStoreId (Long storeId) {
        Long managerId = queryFactory.select(qManager.id)
                                     .from(qManager)
                                     .where(qManager.storeId.eq(storeId))
                                     .fetchOne();

        if (managerId == null) {
            throw new RuntimeException("잘못된 접근 요청");
        }
        return managerId;
    }

    /**
     * 현재 로그인한 manager의 정보로 managerId 가져오기
     */
    public Long getManagerId () {
        // 사용자가 Manager인지는 url을 통해 SpringSecurity로 1차 유효성 검사가 끝난 상태
        String email = SecurityContextHolder.getContext()
                                            .getAuthentication()
                                            .getName();
        log.info("security context holder를 통해 얻은 email: {}", email);
        return queryFactory.selectFrom(qManager)
                           .where(qManager.email.eq(email))
                           .select(qManager.id)
                           .fetchOne();
    }
}
