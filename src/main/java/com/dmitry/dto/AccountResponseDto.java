package com.dmitry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDto {

    @Schema(description = "Текущий баланс аккаунта. Не может быть отрицательным.", example = "725.50")
    @NotNull(message = "Баланс обязателен")
    @DecimalMin(value = "0.00", message = "Баланс не может быть отрицательным")
    private BigDecimal balance;
}
