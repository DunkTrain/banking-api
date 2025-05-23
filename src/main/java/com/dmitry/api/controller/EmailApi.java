package com.dmitry.api.controller;

import com.dmitry.dto.request.AddEmailRequestDto;
import com.dmitry.dto.request.UpdateEmailRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Emails", description = "Управление email-адресами пользователя")
@RequestMapping("/api/users/emails")
public interface EmailApi {

    @PostMapping
    @Operation(summary = "Добавить новый email пользователю")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Email успешно добавлен"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "409", description = "Email уже существует")
    })
    ResponseEntity<Void> addEmail(
            @RequestBody @Valid AddEmailRequestDto request
    );

    @PatchMapping("/{emailId}")
    @Operation(summary = "Обновить существующий email пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Email успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "403", description = "Нет прав на изменение"),
            @ApiResponse(responseCode = "404", description = "Email не найден")
    })
    ResponseEntity<Void> updateEmail(
            @PathVariable Long emailId,
            @RequestBody @Valid UpdateEmailRequestDto request
    );

    @DeleteMapping("/{emailId}")
    @Operation(summary = "Удалить email пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Email успешно удален"),
            @ApiResponse(responseCode = "403", description = "Нет прав на удаление"),
            @ApiResponse(responseCode = "404", description = "Email не найден")
    })
    ResponseEntity<Void> deleteEmail(
            @PathVariable Long emailId
    );
}
