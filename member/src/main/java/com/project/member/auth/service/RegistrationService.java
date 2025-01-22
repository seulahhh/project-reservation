package com.project.member.auth.service;

import com.project.member.domain.entity.Customer;
import com.project.member.domain.entity.Manager;
import com.project.member.domain.repository.CustomerRepository;
import com.project.member.domain.repository.ManagerRepository;
import com.project.member.model.dto.SignupForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final CustomerRepository customerRepository;
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerCustomer (SignupForm form) {
        if (customerRepository.existsByEmail(form.getEmail())) {
            throw new IllegalArgumentException("Customer already exists!");
        }
        Customer customer = Customer.builder()
                .name(form.getName())
                .email(form.getEmail())
                .enabled(true)
                .password(passwordEncoder.encode(form.getPassword()))
                .build();
        customerRepository.save(customer);
    }

    public void registerManager (SignupForm form) {
        if (managerRepository.existsByEmail(form.getEmail())) {
            throw new IllegalArgumentException("Manager already exists!");
        }
        Manager manager = Manager.builder()
                .name(form.getName())
                .email(form.getEmail())
                .enabled(true)
                .password(passwordEncoder.encode(form.getPassword()))
                .build();
        managerRepository.save(manager);
    }
}
