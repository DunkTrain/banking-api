package com.dmitry.dto.responce;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO для отображения полной информации о пользователе.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    @Schema(description = "Идентификатор пользователя")
    private Long id;

    @Schema(description = "Имя пользователя")
    private String name;

    @Schema(description = "Дата рождения пользователя")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirthAfter;

    @Schema(description = "Список email-адресов пользователя")
    private Set<EmailResponseDto> emails;

    @Schema(description = "Список телефонов пользователя")
    private Set<PhoneResponseDto> phones;

    @Schema(description = "Баланс аккаунта пользователя")
    private BigDecimal balance;
}
