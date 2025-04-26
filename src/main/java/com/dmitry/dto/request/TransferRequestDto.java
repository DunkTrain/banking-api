package com.dmitry.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * DTO для запроса перевода средств от одного пользователя другому.
 */
public record TransferRequestDto(

        @Schema(description = "ID пользователя-получателя", example = "2")
        @NotNull(message = "ID получателя обязательно")
        Long toUserId,

        @Schema(description = "Сумма перевода", example = "250.00")
        @NotNull(message = "Сумма перевода обязательно")
        @DecimalMin(value = "0.01", message = "Сумма перевода должна быть больше нуля")
        BigDecimal amount

) { }
