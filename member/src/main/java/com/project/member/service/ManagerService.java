package com.project.member.service;

import com.project.member.exception.CustomException;
import com.project.member.exception.ErrorCode;
import com.project.member.persistence.entity.*;
import com.project.member.persistence.repository.ManagerRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.member.exception.ErrorCode.STORE_NOT_FOUND;


@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final JPAQueryFactory queryFactory;
    QManager qManager = QManager.manager;

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
     * managerId로 storeId 가져오기
     */
    public Long getStoreIdFromManagerId (Long managerId) {
        Long storeId = queryFactory.select(qManager.storeId)
                             .from(qManager)
                             .where(qManager.id.eq(managerId))
                             .fetchOne();
        if (storeId == null) {
            throw new CustomException(STORE_NOT_FOUND);
        }
        return storeId;
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

    /**
     * 현재 로그인한 매니저가 등록한 매장이 있는지 확인
     */
    public boolean hasRegistered() {
        Long managerId = getManagerId();
        if(!queryFactory.select(qManager.storeId)
                      .from(qManager)
                      .where(qManager.id.eq(managerId))
                      .fetch()
                      .isEmpty()) {
            return true;
        }
        return false;
    }
}
