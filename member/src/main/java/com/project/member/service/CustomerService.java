package com.project.member.service;

import com.project.member.exception.CustomException;
import com.project.member.model.dto.CustomerDto;
import com.project.member.persistence.entity.Customer;
import com.project.member.persistence.repository.CustomerRepository;
import com.project.member.util.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import static com.project.member.exception.ErrorCode.*;

/**
 * distance, searching...
 */
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    /**
     * 현재 로그인한 사용자의 email을 가져오기
     */
    public String getCurrentUserEmail() {
        return SecurityContextHolder.getContext()
                                    .getAuthentication()
                                    .getName();
    }

    /**
     * 현재 로그인한 customer의 정보로 customerId 가져오기
     */
    public Long getCurrentCustomerId () {
        String email = getCurrentUserEmail();
        Customer customer = customerRepository.findByEmail(email)
                                              .orElseThrow(() -> new CustomException(USER_NOT_FOUND));// todo
        return customer.getId();
    }

    /**
     * 현재 로그인한 customer가져오기 (dto)
     *
     */
    public CustomerDto getCurrentCustomerDto () {
        String email = SecurityContextHolder.getContext()
                                           .getAuthentication()
                                           .getName();
        Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        return customerMapper.toDto(customer);
    }

    /**
     * customer Id 로 세부정보 가져오기
     */
    public CustomerDto getCustomerDtoFromId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                                              .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        return customerMapper.toDto(customer);
    }

    /**
     * 예약자 정보 주기
     */
}
