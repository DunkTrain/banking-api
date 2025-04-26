package com.dmitry.dto.responce;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO для передачи параметров фильтрации при поиске пользователей.
 * <p>
 * Поддерживает фильтры по имени (начало строки), email, телефону (полное совпадение),
 * а также по дате рождения (позже указанной).
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
public class UserSearchCriteriaDto {

    @Schema(description = "Фильтр по имени (поиск по началу имени)", example = "Алекс")
    private String name;

    @Schema(description = "Фильтр по email (полное совпадение)", example = "user@example.com")
    private String email;

    @Schema(description = "Фильтр по телефону (полное совпадение)", example = "79207865432")
    private String phone;

    @Schema(description = "Фильтр по дате рождения — выбираются пользователи, родившиеся ПОЗЖЕ указанной даты", example = "2000-01-01")
    private LocalDate dateOfBirthAfter;
}
