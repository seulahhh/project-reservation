package com.project.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    public void storeToken(String email, String token) {
        long expirationTimeInSeconds = 3600; // 만료시간 1시간으로 지정

        redisTemplate.opsForValue()
                     .set(email, token, expirationTimeInSeconds, TimeUnit.HOURS);
        log.info("stored complete @@ token : {}", token);
    }


    public String getTokenFromRedis(String email) {
        String token = redisTemplate.opsForValue().get(email);
        log.info("email: {}", email);
        log.info("found complete @@ token : {}", token);
        return token;
    }
}
