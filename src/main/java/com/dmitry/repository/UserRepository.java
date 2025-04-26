package com.dmitry.repository;

import com.dmitry.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью {@link Users}
 * <p>
 * Обеспечивает доступ к пользовательским данным, включая связанные сущности:
 * email-адреса, номера телефонов и счетов.
 */
public interface UserRepository extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {

    /**
     * Проверяет наличие пользователя, связанного с указанным email.
     *
     * @param email значение email-адреса
     * @return {@code true}, если такой пользователь существует, иначе {@code false}
     */
    boolean existsByEmails_Email(String email);

    /**
     * Проверяет наличие пользователя, связанного с указанным телефоном.
     *
     * @param phone значение телефонного номера
     * @return {@code true}, если такой пользователь существует, иначе {@code false}
     */
    boolean existsByPhones_Phone(String phone);

    /**
     * Возвращает пользователя по идентификатору вместе с загруженными
     * связанными сущностями: email, phone и account.
     *
     * @param id идентификатор пользователя
     * @return {@link Optional} с пользователем, если найден
     */
    @Override
    @EntityGraph(attributePaths = { "emails", "phones", "account" })
    Optional<Users> findById(Long id);

    /**
     * Ищет пользователей по спецификации с подгрузкой связанных сущностей.
     *
     * @param spec спецификация поиска
     * @param pageable параметры пагинации
     * @return страница пользователей
     */
    @EntityGraph(attributePaths = {"account"})
    Page<Users> findAll(@Nullable Specification<Users> spec, Pageable pageable);

    // TODO: Добавить email для аутентификации
}
