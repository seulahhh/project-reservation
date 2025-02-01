package com.project.reservation.persistence.repository.store;

import com.project.reservation.persistence.entity.Store;

import java.util.Optional;

public interface StoreRepositoryCustom {
    Optional<Store> findStoreByManagerId(Long managerId);
}
