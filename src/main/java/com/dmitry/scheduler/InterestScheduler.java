package com.dmitry.scheduler;

import com.dmitry.entity.Account;
import com.dmitry.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.stream.Stream;

/**
 * Планировщик начисления процентов пользователям.
 * <p>
 * Запускается каждые 30 секунд и увеличивает баланс аккаунтов на заданный процент,
 * не превышая ограничение в 207% от начального депозита.
 * Использует ShedLock для обеспечения запуска только одним инстансом приложения.
 */
@Component
@RequiredArgsConstructor
public class InterestScheduler {

    private final AccountRepository accountRepository;
    private final InterestAccrualProperties properties;

    /**
     * Периодическая задача начисления процентов на балансы аккаунтов.
     * <p>
     * Запускается каждые 30 секунд. Для каждого аккаунта рассчитывается новый баланс:
     * он увеличивается на заданный процент, но не превышает максимальное значение
     * (initBalance * maxMultiplier). Результат сохраняется в базу.
     * <p>
     * Благодаря аннотации {@code @SchedulerLock} метод может выполняться только одним
     * инстансом приложения в распределённой среде.
     */
    @Scheduled(fixedRate = 30_000)
    @SchedulerLock(name = "accrueInterest", lockAtMostFor = "PT30S", lockAtLeastFor = "PT29S")
    @Transactional
    public void accrueInterest() {
        BigDecimal maxMultiplier = properties.getMaxMultiplier();
        BigDecimal percent = BigDecimal.ONE.add(properties.getPercent());

        try (Stream<Account> stream = accountRepository.streamAllForAccrual()) {
            stream.forEach(account -> {
                BigDecimal current = account.getBalance();
                BigDecimal initial = account.getInitBalance();
                BigDecimal max = initial.multiply(maxMultiplier);
                BigDecimal updated = current.multiply(percent);

                if (updated.compareTo(max) > 0) {
                    updated = max;
                }

                account.setBalance(updated);
                accountRepository.save(account);
            });
        }
    }
}
