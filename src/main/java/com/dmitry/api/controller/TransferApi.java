package com.dmitry.api.controller;

import com.dmitry.dto.request.TransferRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Transfer", description = "Переводы средств между пользователями")
@RequestMapping("/api/transfer")
public interface TransferApi {

    @PostMapping
    @Operation(summary = "Перевести средства другому пользователю")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Перевод выполнен успешно"),
            @ApiResponse(responseCode = "400", description = "Неверные параметры перевода"),
            @ApiResponse(responseCode = "403", description = "Попытка отправить средства самому себе"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "409", description = "Недостаточно средств на счете")
    })
    ResponseEntity<Void> transfer(
            @RequestBody @Valid TransferRequestDto requestDto
    );
}
