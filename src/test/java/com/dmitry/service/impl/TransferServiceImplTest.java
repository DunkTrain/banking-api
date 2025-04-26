package com.dmitry.service.impl;

import com.dmitry.dto.request.TransferRequestDto;
import com.dmitry.entity.Account;
import com.dmitry.entity.Transfer;
import com.dmitry.entity.Users;
import com.dmitry.repository.AccountRepository;
import com.dmitry.repository.TransferRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * {@link TransferServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
public class TransferServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransferRepository transferRepository;

    @InjectMocks
    private TransferServiceImpl transferService;

    @Test
    @DisplayName("Успешный перевод средств между пользователями")
    void shouldTransferMoneySuccessfully() {
        Long fromUserId = 1L;
        Long toUserId = 2L;
        BigDecimal amount = BigDecimal.valueOf(100);

        Users fromUser = new Users();
        fromUser.setId(fromUserId);

        Users toUser = new Users();
        toUser.setId(toUserId);

        Account fromAccount = new Account();
        fromAccount.setBalance(BigDecimal.valueOf(500));
        fromAccount.setInitBalance(BigDecimal.valueOf(500));
        fromAccount.setUser(fromUser);

        Account toAccount = new Account();
        toAccount.setBalance(BigDecimal.valueOf(200));
        toAccount.setInitBalance(BigDecimal.valueOf(200));
        toAccount.setUser(toUser);

        when(accountRepository.findByUserId(fromUserId)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByUserId(toUserId)).thenReturn(Optional.of(toAccount));

        TransferRequestDto request = new TransferRequestDto(toUserId, amount);

        transferService.transfer(fromUserId, request);

        verify(accountRepository).saveAll(anyList());
        verify(transferRepository).save(any(Transfer.class));

        assertThat(fromAccount.getBalance()).isEqualByComparingTo("400");
        assertThat(toAccount.getBalance()).isEqualByComparingTo("300");
    }

    @Test
    @DisplayName("Перевод должен завершиться ошибкой при недостаточном балансе отправителя")
    void shouldThrowExceptionWhenNotEnoughBalance() {
        Long fromUserId = 1L;
        Long toUserId = 2L;
        BigDecimal amount = BigDecimal.valueOf(600);

        Users fromUser = new Users();
        fromUser.setId(fromUserId);

        Users toUser = new Users();
        toUser.setId(toUserId);

        Account fromAccount = new Account();
        fromAccount.setBalance(BigDecimal.valueOf(500));
        fromAccount.setInitBalance(BigDecimal.valueOf(500));
        fromAccount.setUser(fromUser);

        Account toAccount = new Account();
        toAccount.setBalance(BigDecimal.valueOf(200));
        toAccount.setInitBalance(BigDecimal.valueOf(200));
        toAccount.setUser(toUser);

        when(accountRepository.findByUserId(fromUserId)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByUserId(toUserId)).thenReturn(Optional.of(toAccount));

        TransferRequestDto request = new TransferRequestDto(toUserId, amount);

        assertThatThrownBy(() -> transferService.transfer(fromUserId, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Недостаточно средств для перевода");

        verify(transferRepository, never()).save(any());
        verify(accountRepository, never()).saveAll(anyList());
    }

    @Test
    @DisplayName("Перевод самому себе должен завершиться ошибкой")
    void shouldThrowExceptionWhenTransferToSelf() {
        Long fromUserId = 1L;
        Long toUserId = 1L;
        BigDecimal amount = BigDecimal.valueOf(100);

        TransferRequestDto request = new TransferRequestDto(toUserId, amount);

        assertThatThrownBy(() -> transferService.transfer(fromUserId, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Нельзя перевести деньги самому себе");

        verifyNoInteractions(accountRepository, transferRepository);
    }
}
