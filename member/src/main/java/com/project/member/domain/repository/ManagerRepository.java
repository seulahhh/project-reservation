package com.project.member.domain.repository;


import com.project.member.domain.entity.Customer;
import com.project.member.domain.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByEmailAndPassword(String email, String password);

    Optional<Manager> findByEmail (String email);

    boolean existsByEmail(String email);
}
