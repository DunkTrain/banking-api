package com.dmitry.repository;

import com.dmitry.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

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
     * Возвращает объект телефона по его значению.
     *
     * @param phone значение телефонного номера
     * @return {@link Optional} с найденным объектом, либо пустой, если не найден
     */
    Optional<Phone> findByPhone(String phone);
}
