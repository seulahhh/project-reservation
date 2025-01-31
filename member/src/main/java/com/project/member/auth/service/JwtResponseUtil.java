package com.project.member.auth.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


public class JwtResponseUtil {

    public static void addJwtToCookie(HttpServletResponse response, String jwtToken) {
        Cookie cookie = new Cookie("JWT-TOKEN", jwtToken);
        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600); // 1시간

        response.addCookie(cookie);
    }
}
