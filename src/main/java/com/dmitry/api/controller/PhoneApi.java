package com.dmitry.api.controller;

import com.dmitry.dto.request.AddPhoneRequestDto;
import com.dmitry.dto.request.UpdatePhoneRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Tag(name = "Phones", description = "Управление телефонными номерами пользователя")
@RequestMapping("/api/users/phones")
public interface PhoneApi {

    @PostMapping
    @Operation(summary = "Добавить новый телефон пользователю")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Телефон успешно добавлен"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Телефон уже существует")
    })
    ResponseEntity<Void> addPhone(
            @RequestBody @Valid AddPhoneRequestDto request
    );

    @PatchMapping("/{phoneId}")
    @Operation(summary = "Обновить существующий телефон пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Телефон успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "403", description = "Нет прав на изменение"),
            @ApiResponse(responseCode = "404", description = "Телефон не найден")
    })
    ResponseEntity<Void> updatePhone(
            @PathVariable Long phoneId,
            @RequestBody @Valid UpdatePhoneRequestDto request
    );

    @DeleteMapping("/{phoneId}")
    @Operation(summary = "Удалить телефон пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Телефон успешно удален"),
            @ApiResponse(responseCode = "403", description = "Нет прав на удаление"),
            @ApiResponse(responseCode = "404", description = "Телефон не найден")
    })
    ResponseEntity<Void> deletePhone(
            @PathVariable Long phoneId
    );
}
