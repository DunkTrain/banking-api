package com.dmitry.service;

/**
 * Сервис для управления email-адресами пользователя.
 * Отвечает за добавление, обновление и удаление email
 */
public interface EmailService {

    /**
     * Добавляет новый email пользователю.
     *
     * @param userId идентификатор пользователя
     * @param email email-адрес
     */
    void addEmail(Long userId, String email);

    /**
     * Обновляет существующий email пользователя.
     *
     * @param userId идентификатор пользователя (проверка доступа)
     * @param emailId идентификатор email-записи
     * @param newEmail новый email
     */
    void updateEmail(Long userId, Long emailId, String newEmail);

    /**
     * Удаляет email пользователя.
     *
     * @param userId идентификатор пользователя (проверка доступа)
     * @param emailId идентификатор email-записи
     */
    void removeEmail(Long userId, Long emailId);
}
