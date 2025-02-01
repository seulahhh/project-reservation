package com.project.reservation.persistence.repository.store;

import com.project.reservation.exception.CustomException;
import com.project.reservation.exception.ErrorCode;
import com.project.reservation.persistence.entity.QStore;
import com.project.reservation.persistence.entity.Store;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryCustom{
private final JPAQueryFactory queryFactory;
private final QStore qStore = QStore.store;
    @Override
    public Optional<Store> findStoreByManagerId (Long managerId) {
        Store store = queryFactory.selectFrom(qStore)
                                        .where(qStore.managerId.eq(managerId))
                                        .fetchOne();
        return Optional.ofNullable(store);
    }


}

