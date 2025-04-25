package com.dmitry.dto.responce;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для отображения email-адреса пользователя.
 */
@Getter
@Setter
@AllArgsConstructor
public class EmailResponseDto {

    @Schema(description = "Email-адрес пользователя", example = "user@example.com")
    private String email;
}
