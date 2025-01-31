package com.project.member.util.mapper;

import com.project.member.model.dto.CustomerDto;
import com.project.member.persistence.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer from (CustomerDto customerDto) {
        return Customer.builder()
                .id(customerDto.getId())
                .email(customerDto.getEmail())
                .password(customerDto.getPassword())
                .phone(customerDto.getPhone())
                .name(customerDto.getName())
                .build();
    }

    public CustomerDto toDto (Customer customer) {
        return CustomerDto.builder().id(customer.getId())
                .email(customer.getEmail())
                .password(customer.getPassword())
                .name(customer.getName())
                .phone(customer.getPhone())
                .build();
    }
}
