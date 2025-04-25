package com.dmitry.service;

import com.dmitry.dto.UsersDTO;

/**
 * Сервис для работы с пользователями.
 * <p>
 * Отвечает за получение профиля текущего пользователя.
 */
public interface UserService {

    /**
     * Возвращает полную информацию о текущем пользователе по его идентификатору.
     *
     * @param userId идентификатор пользователя
     * @return DTO с данными пользователя
     */
    UsersDTO getProfile(Long userId);
}
