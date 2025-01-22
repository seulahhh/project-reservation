package com.project.member.service;

import com.project.member.domain.entity.Customer;
import com.project.member.domain.entity.Manager;
import com.project.member.domain.repository.ManagerRepository;
import com.project.member.model.dto.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 사용 X
 */
@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;
    public String getManager(LoginForm form) {
        Manager manager = managerRepository.findByEmailAndPassword(form.getEmail(),
                                                                    form.getPassword()).orElseThrow(() ->
                new RuntimeException("사용자를 찾을 수 없습니다 ") //TODO
        );
        return form.getRole();
    }
}
