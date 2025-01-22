package com.project.member.service;

import com.project.member.domain.entity.Customer;
import com.project.member.domain.repository.CustomerRepository;
import com.project.member.model.dto.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 사용 X
 */
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public String getCustomer(LoginForm form) {
        Customer customer = customerRepository.findByEmailAndPassword(form.getEmail(),
                                                                     form.getPassword()).orElseThrow(() ->
                new RuntimeException("사용자를 찾을 수 없습니다") // TODO customException 처리
                                                                                   );
        return form.getRole();
    }
}
