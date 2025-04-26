package com.dmitry.service;

import com.dmitry.dto.request.TransferRequestDto;

/**
 * Сервис для перевода средств между пользователями.
 */
public interface TransferService {

    /**
     * Переводит средства от одного пользователя другому.
     *
     * @param fromUserID ID отправителя (из токена)
     * @param request параметры перевода
     */
    void transfer(Long fromUserID, TransferRequestDto request);
}
