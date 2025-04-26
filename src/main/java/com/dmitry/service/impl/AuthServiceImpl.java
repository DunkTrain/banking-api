package com.dmitry.service.impl;

import com.dmitry.config.security.jwt.JwtProvider;
import com.dmitry.dto.auth.LoginRequestDto;
import com.dmitry.dto.auth.LoginResponseDto;
import com.dmitry.entity.Users;
import com.dmitry.repository.UserRepository;
import com.dmitry.service.AuthService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        Users user;

        if (StringUtils.hasText(request.getEmail())) {
            user = userRepository.findByEmails_Email(request.getEmail())
                    .orElseThrow(() -> new EntityNotFoundException("Пользователь с таким email не найден"));
        } else if (StringUtils.hasText(request.getPhone())) {
            user = userRepository.findByPhones_Phone(request.getPhone())
                    .orElseThrow(() -> new EntityNotFoundException("Пользователь с таким телефоном не найден"));
        } else {
            throw new IllegalArgumentException("Необходимо указать email или phone");
        }

        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Неверный пароль");
        }

        String token = jwtProvider.generateToken(user.getId());
        return new LoginResponseDto(token);
    }
}
