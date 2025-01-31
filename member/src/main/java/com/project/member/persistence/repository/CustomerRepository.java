package com.project.member.persistence.repository;

import com.project.member.persistence.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCustom {
    Optional<Customer> findByEmailAndPassword(String email, String password);

    Optional<Customer> findByEmail (String email);

    boolean existsByEmail(String email);
}
