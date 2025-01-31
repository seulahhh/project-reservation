package com.project.reservation.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class JwtResponseUtil {
    public static void addJwtToCookie(HttpServletResponse response, String jwtToken) {
        // JWT 토큰을 쿠키에 추가
        Cookie cookie = new Cookie("jwtToken", jwtToken);
        cookie.setHttpOnly(true);  // JavaScript에서 쿠키에 접근할 수 없도록 설정
        cookie.setSecure(true);    // HTTPS 연결에서만 쿠키 전송 (보안 강화)
        cookie.setPath("/");       // 쿠키의 유효 경로 설정
        cookie.setMaxAge(3600);    // 쿠키 만료 시간 설정 (1시간)
        cookie.setDomain("localhost"); // 쿠키 도메인 설정 (필요시)

        // 응답에 쿠키 추가
        response.addCookie(cookie);
    }
}
