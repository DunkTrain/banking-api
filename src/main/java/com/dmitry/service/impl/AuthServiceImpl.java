package com.dmitry.service.impl;

import com.dmitry.config.security.jwt.JwtProvider;
import com.dmitry.dto.auth.LoginRequestDto;
import com.dmitry.dto.auth.LoginResponseDto;
import com.dmitry.entity.Users;
import com.dmitry.repository.UserRepository;
import com.dmitry.service.AuthService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        Users user;

        if (StringUtils.hasText(request.getEmail())) {
            user = userRepository.findByEmails_Email(request.getEmail())
                    .orElseThrow(() -> {
                        log.warn("Попытка входа с несуществующим email: {}", request.getEmail());
                        return new EntityNotFoundException("Пользователь с таким email не найден");
                    });
        } else if (StringUtils.hasText(request.getPhone())) {
            user = userRepository.findByPhones_Phone(request.getPhone())
                    .orElseThrow(() -> {
                        log.warn("Попытка входа с несуществующим телефоном: {}", request.getPhone());
                        return new EntityNotFoundException("Пользователь с таким телефоном не найден");
                    });
        } else {
            log.warn("Попытка входа без email и телефона");
            throw new IllegalArgumentException("Необходимо указать email или phone");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Неверный пароль при попытке входа для пользователя ID: {}", user.getId());
            throw new IllegalArgumentException("Неверный пароль");
        }

        log.info("Успешный вход пользователя с ID: {}", user.getId());

        String token = jwtProvider.generateToken(user.getId());
        return new LoginResponseDto(token);
    }
}
