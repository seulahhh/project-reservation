package com.project.member.domain.repository;

import com.project.member.domain.entity.Customer;
import com.project.member.domain.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmailAndPassword(String email, String password);

    Optional<Customer> findByEmail (String email);

    boolean existsByEmail(String email);

}
