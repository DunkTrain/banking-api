package com.dmitry.service.impl;

import com.dmitry.dto.UserSearchCriteriaDto;
import com.dmitry.dto.responce.UserResponseDto;
import com.dmitry.entity.Users;
import com.dmitry.mapper.UserMapper;
import com.dmitry.repository.UserRepository;
import com.dmitry.service.UserService;
import com.dmitry.spec.UserSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public UserResponseDto getProfile(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        return userMapper.toDto(user);
    }

    @Override
    public Page<UserResponseDto> searchUsers(UserSearchCriteriaDto criteria, Pageable pageable) {
        Specification<Users> spec = UserSpecification.withFilters(criteria);
        Page<Users> userPage = userRepository.findAll(spec, pageable);

        return userPage.map(userMapper::toDto);
    }
}
