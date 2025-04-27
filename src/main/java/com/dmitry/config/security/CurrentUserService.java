package com.dmitry.config.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Сервис для получения информации о текущем аутентифицированном пользователе.
 * <p>
 * Используется для извлечения идентификатора пользователя (userId) из {@link SecurityContextHolder},
 * где userId устанавливается на этапе обработки JWT токена.
 * <p>
 * Предназначен для использования в слоях контроллеров и сервисов,
 * чтобы избежать прямого обращения к SecurityContext в разных частях приложения.
 */
@Component
public class CurrentUserService {

    /**
     * Возвращает идентификатор текущего пользователя, извлечённый из контекста безопасности.
     *
     * @return ID текущего аутентифицированного пользователя
     * @throws ClassCastException если principal не является Long
     */
    public Long getCurrentUserId() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
