package com.project.member.domain.repository;

import com.project.member.domain.entity.Manager;
import com.project.member.domain.entity.Store;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    @EntityGraph(attributePaths = {"reviews"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Store> findByName(String name);
    Optional<Store> findByManager_Id (Long managerId);
}
