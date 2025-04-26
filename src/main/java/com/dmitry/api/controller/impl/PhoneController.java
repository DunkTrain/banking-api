package com.dmitry.api.controller.impl;

import com.dmitry.api.controller.PhoneApi;
import com.dmitry.dto.request.AddPhoneRequestDto;
import com.dmitry.dto.request.UpdatePhoneRequestDto;
import com.dmitry.service.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class PhoneController implements PhoneApi {

    private final PhoneService phoneService;

    @Override
    public ResponseEntity<Void> addPhone(Long userId, AddPhoneRequestDto request) {
        phoneService.addPhone(userId, request.getPhone());
        return ResponseEntity.status(201).build();
    }

    @Override
    public ResponseEntity<Void> updatePhone(Long userId, Long phoneId, UpdatePhoneRequestDto request) {
        phoneService.updatePhone(userId, phoneId, request.getNewPhone());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deletePhone(Long userId, Long phoneId) {
        phoneService.removePhone(userId, phoneId);
        return ResponseEntity.noContent().build();
    }
}
