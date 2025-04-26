package com.dmitry.api.controller;

import com.dmitry.dto.responce.UserResponseDto;
import com.dmitry.dto.responce.UserSearchCriteriaDto;
import com.dmitry.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * Контроллер для управления пользователями
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    /**
     * Поиск пользователя с фильтрацией по имени, email, телефону и дате рождения.
     *
     * @param name             часть имени (поиск по началу)
     * @param email            полный email
     * @param phone            полный телефон
     * @param dateOfBirthAfter фильтр по дате рождения (пользователи, родившиеся после указанной даты)
     * @param pageable         параметры пагинации
     * @return страница найденных пользователей
     */
    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirthAfter,
            @Valid Pageable pageable
    ) {
        UserSearchCriteriaDto criteria = new UserSearchCriteriaDto(name, email, phone, dateOfBirthAfter);
        Page<UserResponseDto> users = userService.searchUsers(criteria, pageable);

        return ResponseEntity.ok(users);
    }
}
