package com.project.member.service;

import com.project.member.persistence.entity.*;
import com.project.member.persistence.repository.ManagerRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final JPAQueryFactory queryFactory;
    QManager qManager = QManager.manager;


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
