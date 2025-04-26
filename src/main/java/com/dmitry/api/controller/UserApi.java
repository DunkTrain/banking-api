package com.dmitry.api.controller;

import com.dmitry.dto.responce.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Tag(name = "Users", description = "Чтение и поиск пользователей")
public interface UserApi {

    @GetMapping("/api/users")
    @Operation(summary = "Поиск пользователей с фильтрами и пагинацией")
    @Parameters({
            @Parameter(name = "name", description = "Часть имени для поиска (по началу)", example = "Name"),
            @Parameter(name = "email", description = "Полный email для точного поиска", example = "name@example.com"),
            @Parameter(name = "phone", description = "Полный номер телефона для точного поиска", example = "79207865432"),
            @Parameter(name = "dateOfBirthAfter", description = "Пользователи, родившиеся после указанной даты", example = "1990-01-01"),
            @Parameter(name = "page", description = "Номер страницы (начиная с 0)", example = "0"),
            @Parameter(name = "size", description = "Размер страницы (количество записей)", example = "10")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный поиск пользователей"),
            @ApiResponse(responseCode = "400", description = "Ошибка в параметрах поиска")
    })
    ResponseEntity<Page<UserResponseDto>> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirthAfter,
            @ParameterObject Pageable pageable
    );
}
