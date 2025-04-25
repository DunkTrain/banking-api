package com.dmitry.service.impl;

import com.dmitry.dto.UsersDTO;
import com.dmitry.entity.Users;
import com.dmitry.mapper.UserMapper;
import com.dmitry.repository.UserRepository;
import com.dmitry.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация сервиса {@link UserService}.
 * <p>
 * Использует репозиторий пользователей и маппер для преобразования сущности в DTO.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UsersDTO getProfile(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        return userMapper.toDto(user);
    }
}
