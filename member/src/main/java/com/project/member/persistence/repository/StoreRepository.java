package com.project.member.persistence.repository;

import com.project.member.persistence.entity.Manager;
import com.project.member.persistence.entity.Store;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    @EntityGraph(attributePaths = {"reviews"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Store> findByName(String name);
    Optional<Store> findByManager_Id (Long managerId);

    Long manager (Manager manager);
}
