package com.dmitry.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для запроса аутентификации пользователя.
 */
@Getter
@Setter
public class LoginRequestDto {

    @NotBlank(message = "Email обязателен")
    private String email;

    @NotBlank(message = "Телефон обязателен")
    private String phone;

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 8, max = 500, message = "Пароль должен содержать от 8 до 500 символов")
    private String password;
}
