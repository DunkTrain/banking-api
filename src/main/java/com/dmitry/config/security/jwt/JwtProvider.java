package com.dmitry.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Провайдер для работы с JWT токенами.
 * <p>
 * Отвечает за генерацию, валидацию токенов и извлечение данных из них.
 */
@Component
public class JwtProvider {

    private final SecretKey key;
    private final long expirationMs;

    public JwtProvider(
            @Value("${jwt.secret}") String secretB64,
            @Value("${jwt.expiration}") long expirationMs
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretB64);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.expirationMs = expirationMs;
    }

    /**
     * Генерирует JWT токен, содержащий только claim userId.
     *
     * @param userId идентификатор пользователя
     * @return подписанный токен
     */
    public String generateToken(Long userId) {
        return Jwts.builder()
                .claim("userId", userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key)
                .compact();
    }

    /**
     * Проверяет валидность токена (подпись и срок действия).
     *
     * @param token JWT токен
     * @return {@code true}, если токен действителен, иначе {@code false}
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Извлекает userId из валидного токена.
     *
     * @param token JWT токен
     * @return userId пользователя
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("userId", Long.class);
    }
}
