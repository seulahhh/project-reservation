package com.project.global.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecretKeyAlgorithm;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    private final RedisTemplate<String, String> redisTemplate;
//    private final SecretKeyAlgorithm alg = Jwts.KEY.A256GCMKW;
//    private final SecretKey key = Jwts.SIG.HS256.key().build();
    private final AeadAlgorithm enc = Jwts.ENC.A256GCM;

    @Autowired
    public JwtTokenProvider (RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    SecretKey keySpec;


    public String generateToken (String username, String role) {
        String token = Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .encryptWith(keySpec, enc)
                .compact();

        return token;
    }

    public String getUsername (String token) {
        String username = Jwts.parser()
                .decryptWith(keySpec)
                              .build()
                              .parseEncryptedClaims(token)
                              .getPayload()
                              .getSubject();
        return username;
    }

    public String getUserRole (String token) {
        String role = Jwts.parser()
                          .decryptWith(keySpec)
                          .build()
                          .parseEncryptedClaims(token)
                          .getPayload()
                          .get("role", String.class);
        return role;
    }
    public boolean validateToken(String token) {
        log.info("validate 중..");
        try {
            Jwts.parser()
                                 .decryptWith(keySpec)
                                 .build()
                                 .parseEncryptedClaims(token);

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
    @PostConstruct
    public void init() {
        byte[] keyBytes = getSecretKey().getBytes(StandardCharsets.UTF_8);
        this.keySpec = new SecretKeySpec(keyBytes, "AES");
    }
    private String getSecretKey() {
        return redisTemplate.opsForValue()
                     .get("jwtSecretKey");
    }
}
