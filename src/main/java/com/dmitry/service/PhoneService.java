package com.dmitry.service;

/**
 * Сервис для управления phone пользователя.
 * Отвечает за добавление, обновление и удаление phone
 */
public interface PhoneService {

    /**
     * Добавляет новый phone пользователю.
     *
     * @param userId идентификатор пользователя
     * @param phone телефон пользователя
     */
    void addPhone(Long userId, String phone);

    /**
     * Обновляет существующий phone пользователя.
     *
     * @param userId идентификатор пользователя (проверка доступа)
     * @param phoneId идентификатор phone-записи
     * @param newPhone новый phone
     */
    void updatePhone(Long userId, Long phoneId, String newPhone);

    /**
     * Удаляет phone пользователя.
     *
     * @param userId идентификатор пользователя (проверка доступа)
     * @param phoneId идентификатор phone
     */
    void removePhone(Long userId, Long phoneId);
}
