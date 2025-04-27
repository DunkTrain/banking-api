package com.dmitry.api.controller.impl;

import com.dmitry.api.controller.EmailApi;
import com.dmitry.config.security.CurrentUserService;
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
    private final CurrentUserService currentUserService;

    @Override
    public ResponseEntity<Void> addEmail(AddEmailRequestDto request) {
        Long userId = currentUserService.getCurrentUserId();
        emailService.addEmail(userId, request.getEmail());
        return ResponseEntity.status(201).build();
    }

    @Override
    public ResponseEntity<Void> updateEmail(Long emailId, UpdateEmailRequestDto request) {
        Long userId = currentUserService.getCurrentUserId();
        emailService.updateEmail(userId, emailId, request.getNewEmail());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteEmail(Long emailId) {
        Long userId = currentUserService.getCurrentUserId();
        emailService.removeEmail(userId, emailId);
        return ResponseEntity.noContent().build();
    }
}
