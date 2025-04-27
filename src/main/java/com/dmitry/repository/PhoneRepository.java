package com.dmitry.repository;

import com.dmitry.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью {@link Phone}
 * <p>
 * Обеспечивает доступ к данным телефонных номеров пользователей.
 * Используется для поиска и проверки уникальности номеров.
 */
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    /**
     * Проверяет наличие телефонного номера в базе данных.
     *
     * @param phone значение телефонного номера
     * @return {@code true}, если номер существует, иначе {@code false}
     */
    boolean existsByPhone(String phone);

    /**
     * Подсчитывает количество телефонных номеров, привязанных к указанному пользователю.
     *
     * @param userId идентификатор пользователя
     * @return количество телефонных номеров, связанных с пользователем
     */
    long countByUserId(Long userId);
}
