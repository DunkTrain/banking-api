package com.dmitry.api.controller.impl;

import com.dmitry.api.controller.TransferApi;
import com.dmitry.dto.request.TransferRequestDto;
import com.dmitry.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class TransferController implements TransferApi {

    private final TransferService transferService;

    @Override
    public ResponseEntity<Void> transfer(Long fromUserId, TransferRequestDto requestDto) {
        transferService.transfer(fromUserId, requestDto);
        return ResponseEntity.status(201).build();
    }
}
