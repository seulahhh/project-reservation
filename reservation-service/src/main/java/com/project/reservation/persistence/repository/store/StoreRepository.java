package com.project.reservation.persistence.repository.store;

import com.project.reservation.persistence.entity.Store;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> , StoreRepositoryCustom{
    @EntityGraph(attributePaths = {"reviews"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Store> findById(Long storeId);
}
