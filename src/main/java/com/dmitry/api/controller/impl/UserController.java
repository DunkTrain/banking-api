package com.dmitry.api.controller.impl;

import com.dmitry.api.controller.UserApi;
import com.dmitry.dto.responce.UserResponseDto;
import com.dmitry.dto.responce.UserSearchCriteriaDto;
import com.dmitry.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Validated
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<Page<UserResponseDto>> searchUsers(
            String name,
            String email,
            String phone,
            LocalDate dateOfBirthAfter,
            Pageable pageable
    ) {
        UserSearchCriteriaDto criteria = new UserSearchCriteriaDto(name, email, phone, dateOfBirthAfter);
        Page<UserResponseDto> users = userService.searchUsers(criteria, pageable);
        return ResponseEntity.ok(users);
    }
}
