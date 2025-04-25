package com.dmitry.repository;

import com.dmitry.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью {@link Email}
 * <p>
 * Обеспечивает доступ к данным электронных адресов пользователей.
 * Используется для поиска и проверки уникальности email-адресов.
 */
public interface EmailRepository extends JpaRepository<Email, Long> {

    /**
     * Возвращает email-объект, соответсвующий указанному значению.
     *
     * @param email значение email-адреса
     * @return {@link Optional} с найденным email, либо пустой, если не найден
     */
    Optional<Email> findByEmail(String email);

    /**
     * Проверяет наличие email-адреса в базе данных.
     *
     * @param email значение email-адреса
     * @return {@code true}, если email существует, иначе {@code false}
     */
    boolean existsByEmail(String email);

    /**
     * Подсчитывает количество email-адресов, привязанных к указанному пользователю.
     *
     * @param userId идентификатор пользователя
     * @return количество email-адресов, связанных с пользователем
     */
    long countByUserId(Long userId);
}
