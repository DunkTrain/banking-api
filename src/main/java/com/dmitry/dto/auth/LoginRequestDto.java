package com.dmitry.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для запроса аутентификации пользователя.
 */
@Getter
@Setter
@Schema(description = "Данные для логина пользователя")
public class LoginRequestDto {

    @Schema(description = "Email пользователя", example = "example@mail.com")
    private String email;

    @Schema(description = "Телефон пользователя", example = "79207865432")
    private String phone;

    @Schema(description = "Пароль пользователя", example = "example123")
    @Size(min = 8, max = 500, message = "Пароль должен содержать от 8 до 500 символов")
    private String password;
}
