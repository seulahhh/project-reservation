package com.project.member.service;

import com.project.member.persistence.entity.Customer;
import com.project.member.persistence.entity.Manager;
import com.project.member.persistence.repository.CustomerRepository;
import com.project.member.persistence.repository.ManagerRepository;
import com.project.member.model.dto.form.SignupForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final CustomerRepository customerRepository;
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerCustomer (SignupForm form) {
        if (customerRepository.existsByEmail(form.getEmail())) {
            throw new IllegalArgumentException("Customer already exists!");
        }
        Customer customer = Customer.builder()
                .name(form.getName())
                .email(form.getEmail())
                .enabled(true)
                .phone(form.getPhone())
                .password(passwordEncoder.encode(form.getPassword()))
                .build();
        customerRepository.save(customer);
    }

    @Transactional
    public void registerManager (SignupForm form) {
        if (managerRepository.existsByEmail(form.getEmail())) {
            throw new IllegalArgumentException("Manager already exists!");
        }
        Manager manager = Manager.builder()
                .name(form.getName())
                .email(form.getEmail())
                .enabled(true)
                .phone(form.getPhone())
                .password(passwordEncoder.encode(form.getPassword()))
                .build();
        managerRepository.save(manager);
    }
}
