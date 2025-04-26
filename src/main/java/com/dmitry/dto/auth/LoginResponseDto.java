package com.dmitry.dto.auth;

/**
 * DTO для ответа с JWT токеном после успешной аутентификации.
 *
 * @param token выданный токен
 */
public record LoginResponseDto(String token) {
}
