package com.dmitry.service.impl;

import com.dmitry.dto.request.TransferRequestDto;
import com.dmitry.entity.Account;
import com.dmitry.entity.Transfer;
import com.dmitry.repository.AccountRepository;
import com.dmitry.repository.TransferRepository;
import com.dmitry.service.TransferService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Реализация {@link TransferService}
 * Обеспечивает безопасный перевод средств между пользователями с валидацией данных и сохранением истории операций.
 */
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;

    @Override
    @Transactional
    public void transfer(Long fromUserId, TransferRequestDto request) {
        validateTransferRequest(fromUserId, request);

        AccountPair accounts = loadAccountsSafely(fromUserId, request.toUserId());
        processTransfer(accounts, request.amount());
        saveTransferHistory(fromUserId, request.toUserId(), request.amount());
    }

    /**
     * Проверяет корректность запроса на перевод средств
     *
     * @param fromUserId ID отправителя
     * @request данные запроса на перевод
     */
    private void validateTransferRequest(Long fromUserId, TransferRequestDto request) {
        if (request.toUserId() == null) {
            throw new IllegalArgumentException("ID получателя не может быть пустым");
        }

        if (fromUserId.equals(request.toUserId())) {
            throw new IllegalArgumentException("Нельзя перевести деньги самому себе");
        }

        BigDecimal amount = request.amount();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма перевода должна быть больше нуля");
        }
    }

    /**
     * Загружает счета отправителя и получателя в порядке защиты от deadlock.
     * Использует упорядочивание ID для блокировок во избежание взаимоблокировок.
     *
     * @param fromUserId ID отправителя
     * @param toUserId   ID получателя
     * @return объект с двумя банковскими счетами
     */
    private AccountPair loadAccountsSafely(Long fromUserId, Long toUserId) {
        // Гарантируем последовательность получения блокировок
        Long firstId = Math.min(fromUserId, toUserId);
        Long secondId = Math.max(fromUserId, toUserId);

        Account firstAccount = accountRepository.findByUserId(firstId)
                .orElseThrow(() -> new EntityNotFoundException(
                        Objects.equals(firstId, fromUserId)
                                ? "Счет отправителя не найден"
                                : "Счет получателя не найден"));

        Account secondAccount = accountRepository.findByUserId(secondId)
                .orElseThrow(() -> new EntityNotFoundException(
                        Objects.equals(secondId, fromUserId)
                                ? "Счет отправителя не найден"
                                : "Счет получателя не найден"));

        // Определяем какой счет является отправителем, а какой получателем
        Account fromAccount = Objects.equals(fromUserId, firstAccount.getUser().getId())
                ? firstAccount
                : secondAccount;

        Account toAccount = Objects.equals(toUserId, firstAccount.getUser().getId())
                ? firstAccount
                : secondAccount;

        return new AccountPair(fromAccount, toAccount);
    }

    /**
     * Выполняет перевод средств между счетами
     *
     * @param accounts   пара счетов для перевода
     * @param amount     сумма перевода
     */
    private void processTransfer(AccountPair accounts, BigDecimal amount) {
        Account fromAccount = accounts.from();
        Account toAccount = accounts.to();

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Недостаточно средств для перевода");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.saveAll(List.of(fromAccount, toAccount));
    }

    /**
     * Сохраняет историю перевода средств
     *
     * @param fromUserId ID отправителя
     * @param toUserId   ID получателя
     * @param amount     сумма перевода
     */
    private void saveTransferHistory(Long fromUserId, Long toUserId, BigDecimal amount) {
        Transfer transfer = new Transfer();
        transfer.setFromUserId(fromUserId);
        transfer.setToUserId(toUserId);
        transfer.setAmount(amount);

        transferRepository.save(transfer);
    }

    /**
     * Хранит пару счетов для операции перевода
     */
    private record AccountPair(Account from, Account to) {}
}
