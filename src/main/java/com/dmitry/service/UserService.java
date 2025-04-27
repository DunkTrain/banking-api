package com.dmitry.service;

import com.dmitry.dto.responce.UserSearchCriteriaDto;
import com.dmitry.dto.responce.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Сервис для работы с пользователями.
 * <p>
 * Отвечает за получение профиля текущего пользователя.
 */
public interface UserService {

    /**
     * Поиск пользователей по фильтрам и пагинации.
     *
     * @param criteria параметры фильтрации
     * @param pageable параметры пагинации
     * @return страница пользователей, удовлетворяющих условиям
     */
    Page<UserResponseDto> searchUsers(UserSearchCriteriaDto criteria, Pageable pageable);
}
