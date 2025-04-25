package com.dmitry.repository;

import com.dmitry.entity.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью {@link Account}
 * <p>
 * Обеспечивает доступ к данным счета пользователя и предоставляет методы
 * для поиска по userId и проверки существования счета.
 * <p>
 * Использует Spring Data JPA для автоматической генерации запросов.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Возвращает счет, связанный с указанным userId, применением
     * блокировки на запись (pessimistic write lock).
     * <p>
     * Используется в бизнес-операциях, где необходимо обеспечить
     * консистентность данных и исключить гонки при обновлении баланса.
     *
     * @param userId идентификатор пользователя, для которого запрашивается счет
     * @return {@link Optional} с найденным счетом, либо пустой, если не найден
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findByUserId(Long userId);

    /**
     * Проверяет наличие счета, связанного с указанным userId.
     * <p>
     * Используется при валидации данных или в предварительных проверках
     * перед выполнением операций над счетом.
     *
     * @param userId идентификатор пользователя
     * @return {@code true}, если счет существует, иначе {@code false}
     */
    boolean existsByUserId(Long userId);
}
