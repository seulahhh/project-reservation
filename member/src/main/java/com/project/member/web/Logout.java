package com.project.member.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequiredArgsConstructor
public class Logout {

    // TODO 임시 로직
    @GetMapping("/getAuth")
    @ResponseBody
    public ResponseEntity<String> getAuth () {
        String author = SecurityContextHolder.getContext()
                                             .getAuthentication()
                                             .getAuthorities()
                                             .stream()
                                             .map(GrantedAuthority::getAuthority)
                                             .findFirst()
                                             .orElse("null");
        return ResponseEntity.ok(author);
    }



}
