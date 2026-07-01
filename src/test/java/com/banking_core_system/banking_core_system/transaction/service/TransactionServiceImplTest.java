package com.banking_core_system.banking_core_system.transaction.service;

import com.banking_core_system.banking_core_system.account.entity.Account;
import com.banking_core_system.banking_core_system.account.entity.AccountStatus;
import com.banking_core_system.banking_core_system.account.repository.AccountRepository;
import com.banking_core_system.banking_core_system.exception.*;
import com.banking_core_system.banking_core_system.transaction.dto.*;
import com.banking_core_system.banking_core_system.transaction.entity.Transaction;
import com.banking_core_system.banking_core_system.transaction.entity.TransactionType;
import com.banking_core_system.banking_core_system.transaction.mapper.TransactionMapper;
import com.banking_core_system.banking_core_system.transaction.repository.TransactionRepository;
import com.banking_core_system.banking_core_system.transaction.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void shouldDepositSuccessfully() {

        DepositRequest request = DepositRequest.builder()
                .accountId(1L)
                .amount(BigDecimal.valueOf(500))
                .description("Cash deposit")
                .build();

        Account account = Account.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(1000))
                .status(AccountStatus.ACTIVE)
                .build();

        Transaction transaction = Transaction.builder()
                .id(1L)
                .type(TransactionType.DEPOSIT)
                .amount(request.getAmount())
                .destinationAccount(account)
                .description(request.getDescription())
                .build();

        TransactionResponse response = TransactionResponse.builder()
                .id(1L)
                .type(TransactionType.DEPOSIT)
                .amount(BigDecimal.valueOf(500))
                .destinationAccountId(1L)
                .description("Cash deposit")
                .build();

        when(accountRepository.findWithLockById(1L))
                .thenReturn(Optional.of(account));

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(transaction);

        when(transactionMapper.toResponse(transaction))
                .thenReturn(response);

        TransactionResponse result = transactionService.deposit(request);

        assertNotNull(result);
        assertEquals(TransactionType.DEPOSIT, result.type());
        assertEquals(BigDecimal.valueOf(1500), account.getBalance());

        verify(accountRepository).findWithLockById(1L);
        verify(transactionRepository).save(any(Transaction.class));
        verify(transactionMapper).toResponse(transaction);
    }

    @Test
    void shouldThrowExceptionWhenDepositAccountNotFound() {

        DepositRequest request = DepositRequest.builder()
                .accountId(99L)
                .amount(BigDecimal.valueOf(500))
                .build();

        when(accountRepository.findWithLockById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> transactionService.deposit(request)
        );

        verify(transactionRepository, never()).save(any());
        verifyNoInteractions(transactionMapper);
    }

    @Test
    void shouldThrowExceptionWhenDepositAccountIsClosed() {

        DepositRequest request = DepositRequest.builder()
                .accountId(1L)
                .amount(BigDecimal.valueOf(500))
                .build();

        Account account = Account.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(1000))
                .status(AccountStatus.CLOSED)
                .build();

        when(accountRepository.findWithLockById(1L))
                .thenReturn(Optional.of(account));

        assertThrows(
                InvalidAccountStatusException.class,
                () -> transactionService.deposit(request)
        );

        verify(transactionRepository, never()).save(any());
        verifyNoInteractions(transactionMapper);
    }

    @Test
    void shouldWithdrawSuccessfully() {

        WithdrawalRequest request = WithdrawalRequest.builder()
                .accountId(1L)
                .amount(BigDecimal.valueOf(300))
                .description("ATM Withdrawal")
                .build();

        Account account = Account.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(1000))
                .status(AccountStatus.ACTIVE)
                .build();

        Transaction transaction = Transaction.builder()
                .id(1L)
                .type(TransactionType.WITHDRAWAL)
                .amount(request.getAmount())
                .sourceAccount(account)
                .description(request.getDescription())
                .build();

        TransactionResponse response = TransactionResponse.builder()
                .id(1L)
                .type(TransactionType.WITHDRAWAL)
                .amount(BigDecimal.valueOf(300))
                .sourceAccountId(1L)
                .description("ATM Withdrawal")
                .build();

        when(accountRepository.findWithLockById(1L))
                .thenReturn(Optional.of(account));

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(transaction);

        when(transactionMapper.toResponse(transaction))
                .thenReturn(response);

        TransactionResponse result = transactionService.withdraw(request);

        assertNotNull(result);
        assertEquals(TransactionType.WITHDRAWAL, result.type());
        assertEquals(BigDecimal.valueOf(700), account.getBalance());

        verify(accountRepository).findWithLockById(1L);
        verify(transactionRepository).save(any(Transaction.class));
        verify(transactionMapper).toResponse(transaction);
    }

    @Test
    void shouldThrowExceptionWhenWithdrawalAccountNotFound() {

        WithdrawalRequest request = WithdrawalRequest.builder()
                .accountId(99L)
                .amount(BigDecimal.valueOf(100))
                .build();

        when(accountRepository.findWithLockById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> transactionService.withdraw(request)
        );

        verify(transactionRepository, never()).save(any());
        verifyNoInteractions(transactionMapper);
    }

    @Test
    void shouldThrowExceptionWhenWithdrawalAccountIsClosed() {

        WithdrawalRequest request = WithdrawalRequest.builder()
                .accountId(1L)
                .amount(BigDecimal.valueOf(100))
                .build();

        Account account = Account.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(1000))
                .status(AccountStatus.CLOSED)
                .build();

        when(accountRepository.findWithLockById(1L))
                .thenReturn(Optional.of(account));

        assertThrows(
                InvalidAccountStatusException.class,
                () -> transactionService.withdraw(request)
        );

        verify(transactionRepository, never()).save(any());
        verifyNoInteractions(transactionMapper);
    }

    @Test
    void shouldThrowExceptionWhenInsufficientFunds() {

        WithdrawalRequest request = WithdrawalRequest.builder()
                .accountId(1L)
                .amount(BigDecimal.valueOf(1500))
                .build();

        Account account = Account.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(1000))
                .status(AccountStatus.ACTIVE)
                .build();

        when(accountRepository.findWithLockById(1L))
                .thenReturn(Optional.of(account));

        assertThrows(
                InsufficientFundsException.class,
                () -> transactionService.withdraw(request)
        );

        verify(transactionRepository, never()).save(any());
        verifyNoInteractions(transactionMapper);
    }

    @Test
    void shouldTransferSuccessfully() {

        TransferRequest request = TransferRequest.builder()
                .sourceAccountId(1L)
                .destinationAccountId(2L)
                .amount(BigDecimal.valueOf(300))
                .description("Transfer")
                .build();

        Account source = Account.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(1000))
                .status(AccountStatus.ACTIVE)
                .build();

        Account destination = Account.builder()
                .id(2L)
                .balance(BigDecimal.valueOf(500))
                .status(AccountStatus.ACTIVE)
                .build();

        Transaction transaction = Transaction.builder()
                .id(1L)
                .type(TransactionType.TRANSFER)
                .amount(request.getAmount())
                .sourceAccount(source)
                .destinationAccount(destination)
                .build();

        when(accountRepository.findWithLockById(1L))
                .thenReturn(Optional.of(source));

        when(accountRepository.findWithLockById(2L))
                .thenReturn(Optional.of(destination));

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(transaction);

        TransferResponse result = transactionService.transfer(request);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(700), source.getBalance());
        assertEquals(BigDecimal.valueOf(800), destination.getBalance());

        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void shouldThrowExceptionWhenSourceAccountNotFound() {

        TransferRequest request = TransferRequest.builder()
                .sourceAccountId(1L)
                .destinationAccountId(2L)
                .amount(BigDecimal.TEN)
                .build();

        when(accountRepository.findWithLockById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> transactionService.transfer(request)
        );

        verify(transactionRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenDestinationAccountNotFound() {

        TransferRequest request = TransferRequest.builder()
                .sourceAccountId(1L)
                .destinationAccountId(2L)
                .amount(BigDecimal.TEN)
                .build();

        Account source = Account.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(1000))
                .status(AccountStatus.ACTIVE)
                .build();

        when(accountRepository.findWithLockById(1L))
                .thenReturn(Optional.of(source));

        when(accountRepository.findWithLockById(2L))
                .thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> transactionService.transfer(request)
        );

        verify(transactionRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenSourceAccountIsClosed() {

        TransferRequest request = TransferRequest.builder()
                .sourceAccountId(1L)
                .destinationAccountId(2L)
                .amount(BigDecimal.TEN)
                .build();

        Account source = Account.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(1000))
                .status(AccountStatus.CLOSED)
                .build();

        Account destination = Account.builder()
                .id(2L)
                .balance(BigDecimal.valueOf(500))
                .status(AccountStatus.ACTIVE)
                .build();

        when(accountRepository.findWithLockById(1L)).thenReturn(Optional.of(source));
        when(accountRepository.findWithLockById(2L)).thenReturn(Optional.of(destination));

        assertThrows(
                InvalidAccountStatusException.class,
                () -> transactionService.transfer(request)
        );
    }

    @Test
    void shouldThrowExceptionWhenDestinationAccountIsClosed() {

        TransferRequest request = TransferRequest.builder()
                .sourceAccountId(1L)
                .destinationAccountId(2L)
                .amount(BigDecimal.TEN)
                .build();

        Account source = Account.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(1000))
                .status(AccountStatus.ACTIVE)
                .build();

        Account destination = Account.builder()
                .id(2L)
                .balance(BigDecimal.valueOf(500))
                .status(AccountStatus.CLOSED)
                .build();

        when(accountRepository.findWithLockById(1L)).thenReturn(Optional.of(source));
        when(accountRepository.findWithLockById(2L)).thenReturn(Optional.of(destination));

        assertThrows(
                InvalidAccountStatusException.class,
                () -> transactionService.transfer(request)
        );
    }

    @Test
    void shouldThrowExceptionWhenSameAccountTransfer() {

        TransferRequest request = TransferRequest.builder()
                .sourceAccountId(1L)
                .destinationAccountId(1L)
                .amount(BigDecimal.TEN)
                .build();

        assertThrows(
                SameAccountTransferException.class,
                () -> transactionService.transfer(request)
        );

        verifyNoInteractions(accountRepository);
        verifyNoInteractions(transactionRepository);
    }

    @Test
    void shouldThrowExceptionWhenTransferInsufficientFunds() {

        TransferRequest request = TransferRequest.builder()
                .sourceAccountId(1L)
                .destinationAccountId(2L)
                .amount(BigDecimal.valueOf(1500))
                .build();

        Account source = Account.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(1000))
                .status(AccountStatus.ACTIVE)
                .build();

        Account destination = Account.builder()
                .id(2L)
                .balance(BigDecimal.valueOf(500))
                .status(AccountStatus.ACTIVE)
                .build();

        when(accountRepository.findWithLockById(1L)).thenReturn(Optional.of(source));
        when(accountRepository.findWithLockById(2L)).thenReturn(Optional.of(destination));

        assertThrows(
                InsufficientFundsException.class,
                () -> transactionService.transfer(request)
        );

        verify(transactionRepository, never()).save(any());
    }

    @Test
    void shouldReturnAccountHistory() {

        Account account = Account.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(1000))
                .status(AccountStatus.ACTIVE)
                .build();

        Transaction transaction1 = Transaction.builder()
                .id(1L)
                .type(TransactionType.DEPOSIT)
                .amount(BigDecimal.valueOf(500))
                .destinationAccount(account)
                .build();

        Transaction transaction2 = Transaction.builder()
                .id(2L)
                .type(TransactionType.WITHDRAWAL)
                .amount(BigDecimal.valueOf(200))
                .sourceAccount(account)
                .build();

        TransactionResponse response1 = TransactionResponse.builder()
                .id(1L)
                .type(TransactionType.DEPOSIT)
                .amount(BigDecimal.valueOf(500))
                .destinationAccountId(1L)
                .build();

        TransactionResponse response2 = TransactionResponse.builder()
                .id(2L)
                .type(TransactionType.WITHDRAWAL)
                .amount(BigDecimal.valueOf(200))
                .sourceAccountId(1L)
                .build();

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(transactionRepository
                .findBySourceAccountOrDestinationAccountOrderByCreatedAtDesc(account, account))
                .thenReturn(List.of(transaction2, transaction1));

        when(transactionMapper.toResponse(transaction2))
                .thenReturn(response2);

        when(transactionMapper.toResponse(transaction1))
                .thenReturn(response1);

        List<TransactionResponse> result =
                transactionService.getAccountHistory(1L);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(accountRepository).findById(1L);
        verify(transactionRepository)
                .findBySourceAccountOrDestinationAccountOrderByCreatedAtDesc(account, account);

        verify(transactionMapper).toResponse(transaction1);
        verify(transactionMapper).toResponse(transaction2);
    }

    @Test
    void shouldThrowExceptionWhenHistoryAccountNotFound() {

        when(accountRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> transactionService.getAccountHistory(99L)
        );

        verify(accountRepository).findById(99L);

        verifyNoInteractions(transactionRepository);
        verifyNoInteractions(transactionMapper);
    }

    @Test
    void shouldReturnTransactionById() {

        Transaction transaction = Transaction.builder()
                .id(1L)
                .type(TransactionType.DEPOSIT)
                .amount(BigDecimal.valueOf(500))
                .description("Cash deposit")
                .build();

        TransactionResponse response = TransactionResponse.builder()
                .id(1L)
                .type(TransactionType.DEPOSIT)
                .amount(BigDecimal.valueOf(500))
                .description("Cash deposit")
                .build();

        when(transactionRepository.findById(1L))
                .thenReturn(Optional.of(transaction));

        when(transactionMapper.toResponse(transaction))
                .thenReturn(response);

        TransactionResponse result =
                transactionService.getTransactionById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(TransactionType.DEPOSIT, result.type());

        verify(transactionRepository).findById(1L);
        verify(transactionMapper).toResponse(transaction);
    }

    @Test
    void shouldThrowExceptionWhenTransactionNotFound() {

        when(transactionRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                TransactionNotFoundException.class,
                () -> transactionService.getTransactionById(99L)
        );

        verify(transactionRepository).findById(99L);
        verifyNoInteractions(transactionMapper);
    }
}
