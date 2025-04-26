package com.dmitry.api.controller;

import com.dmitry.dto.request.TransferRequestDto;
import com.dmitry.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для перевода средств между пользователями.
 */
@RestController
@RequestMapping("/api/transfer")
@RequiredArgsConstructor
@Validated
public class TransferController {

    private final TransferService transferService;

    /**
     * Выполняет перевод средств от текущего пользователя к другому пользователю.
     *
     * @param fromUserId ID отправителя (пока передаем вручную для тестов)
     * @param requestDto параметры перевода
     * @return HTTP 201 Created при успешной операции
     */
    @PostMapping
    public ResponseEntity<Void> transfer(
            @RequestParam Long fromUserId,
            @RequestBody @Valid TransferRequestDto requestDto
    ) {
        transferService.transfer(fromUserId, requestDto);
        return ResponseEntity.status(201).build();
    }
}
