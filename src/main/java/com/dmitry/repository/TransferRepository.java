package com.dmitry.repository;

import com.dmitry.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью {@link Transfer}
 */
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
