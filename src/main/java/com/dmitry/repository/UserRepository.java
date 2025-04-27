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

    /**
     * Ищет пользователя по одному из email.
     *
     * @param email Email адрес пользователя
     * @return найденный пользователь или пусто
     */
    @EntityGraph(attributePaths = {"emails", "phones", "account"})
    Optional<Users> findByEmails_Email(String email);

    /**
     * Ищет пользователя по одному из телефонов.
     *
     * @param phone Телефонный номер пользователя
     * @return найденный пользователь или пусто
     */
    @EntityGraph(attributePaths = {"emails", "phones", "account"})
    Optional<Users> findByPhones_Phone(String phone);
}
