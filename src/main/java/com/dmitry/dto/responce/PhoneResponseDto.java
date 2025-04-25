package com.dmitry.dto.responce;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для отображения телефонного номера пользователя.
 */
@Getter
@Setter
@AllArgsConstructor
public class PhoneResponseDto {

    @Schema(description = "Номер телефона пользователя", example = "79207865432")
    private String phone;
}
