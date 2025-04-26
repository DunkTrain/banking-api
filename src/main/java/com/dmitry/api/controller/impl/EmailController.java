package com.dmitry.api.controller.impl;

import com.dmitry.api.controller.EmailApi;
import com.dmitry.dto.request.AddEmailRequestDto;
import com.dmitry.dto.request.UpdateEmailRequestDto;
import com.dmitry.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class EmailController implements EmailApi {

    private final EmailService emailService;

    @Override
    public ResponseEntity<Void> addEmail(Long userId, AddEmailRequestDto request) {
        emailService.addEmail(userId, request.getEmail());
        return ResponseEntity.status(201).build();
    }

    @Override
    public ResponseEntity<Void> updateEmail(Long userId, Long emailId, UpdateEmailRequestDto request) {
        emailService.updateEmail(userId, emailId, request.getNewEmail());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteEmail(Long userId, Long emailId) {
        emailService.removeEmail(userId, emailId);
        return ResponseEntity.noContent().build();
    }
}
