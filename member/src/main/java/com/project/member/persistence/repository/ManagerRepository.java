package com.project.member.persistence.repository;


import com.project.member.persistence.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByEmailAndPassword(String email, String password);

    Optional<Manager> findByEmail (String email);

    boolean existsByEmail(String email);
}
