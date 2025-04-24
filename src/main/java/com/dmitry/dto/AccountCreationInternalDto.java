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
public class AccountCreationInternalDto {

    @Schema(description = "Начальный баланс аккаунта. Не может быть отрицательным.", example = "1000.00")
    @NotNull(message = "Начальный баланс обязателен")
    @DecimalMin(value = "0.00", message = "Начальный баланс не может быть отрицательным")
    private BigDecimal initialBalance;
}
