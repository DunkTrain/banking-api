package com.dmitry.service;

import com.dmitry.dto.auth.LoginRequestDto;
import com.dmitry.dto.auth.LoginResponseDto;

/**
 * Сервис для аутентификации пользователей.
 */
public interface AuthService {

    /**
     * Аутентифицирует пользователя и выдает JWT токен.
     *
     * @param request данные для входа (email/phone + пароль)
     * @return токен для авторизации
     */
    LoginResponseDto login(LoginRequestDto request);
}
