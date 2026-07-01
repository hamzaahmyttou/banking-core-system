package com.banking_core_system.banking_core_system.transaction.service.impl;

import com.banking_core_system.banking_core_system.account.entity.Account;
import com.banking_core_system.banking_core_system.account.entity.AccountStatus;
import com.banking_core_system.banking_core_system.account.repository.AccountRepository;
import com.banking_core_system.banking_core_system.exception.AccountNotFoundException;
import com.banking_core_system.banking_core_system.exception.InsufficientFundsException;
import com.banking_core_system.banking_core_system.exception.InvalidAccountStatusException;
import com.banking_core_system.banking_core_system.exception.SameAccountTransferException;
import com.banking_core_system.banking_core_system.transaction.dto.*;
import com.banking_core_system.banking_core_system.transaction.entity.Transaction;
import com.banking_core_system.banking_core_system.transaction.entity.TransactionType;
import com.banking_core_system.banking_core_system.transaction.mapper.TransactionMapper;
import com.banking_core_system.banking_core_system.transaction.repository.TransactionRepository;
import com.banking_core_system.banking_core_system.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    private Account getLockedAccount(Long id) {

        return accountRepository.findWithLockById(id)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account with id " + id + " not found"));
    }

    private void validateActiveAccount(Account account) {

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new InvalidAccountStatusException(
                    "Account is not active");
        }
    }

    private void validateSufficientBalance(Account account, BigDecimal amount) {

        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(
                    "Insufficient balance");
        }
    }

    @Override
    public TransactionResponse deposit(DepositRequest request) {

        Account account = getLockedAccount(request.getAccountId());

        validateActiveAccount(account);

        account.setBalance(
                account.getBalance().add(request.getAmount())
        );

        Transaction transaction = Transaction.builder()
                .type(TransactionType.DEPOSIT)
                .amount(request.getAmount())
                .destinationAccount(account)
                .description(request.getDescription())
                .build();

        accountRepository.save(account);

        Transaction savedTransaction =
                transactionRepository.save(transaction);

        return transactionMapper.toResponse(savedTransaction);
    }

    @Override
    public TransactionResponse withdraw(WithdrawalRequest request) {

        Account account = getLockedAccount(request.getAccountId());

        validateActiveAccount(account);
        validateSufficientBalance(account, request.getAmount());

        account.setBalance(
                account.getBalance().subtract(request.getAmount())
        );

        Transaction transaction = Transaction.builder()
                .type(TransactionType.WITHDRAWAL)
                .amount(request.getAmount())
                .sourceAccount(account)
                .description(request.getDescription())
                .build();

        accountRepository.save(account);

        Transaction savedTransaction =
                transactionRepository.save(transaction);

        return transactionMapper.toResponse(savedTransaction);
    }

    @Override
    public TransferResponse transfer(TransferRequest request) {

        if (request.getSourceAccountId().equals(request.getDestinationAccountId())) {
            throw new SameAccountTransferException(
                    "Source and destination accounts must be different");
        }

        Long firstId = Math.min(
                request.getSourceAccountId(),
                request.getDestinationAccountId());

        Long secondId = Math.max(
                request.getSourceAccountId(),
                request.getDestinationAccountId());

        Account firstLocked = getLockedAccount(firstId);
        Account secondLocked = getLockedAccount(secondId);

        Account sourceAccount =
                firstLocked.getId().equals(request.getSourceAccountId())
                        ? firstLocked
                        : secondLocked;

        Account destinationAccount =
                firstLocked.getId().equals(request.getDestinationAccountId())
                        ? firstLocked
                        : secondLocked;

        validateActiveAccount(sourceAccount);
        validateActiveAccount(destinationAccount);

        validateSufficientBalance(sourceAccount, request.getAmount());

        sourceAccount.setBalance(
                sourceAccount.getBalance().subtract(request.getAmount()));

        destinationAccount.setBalance(
                destinationAccount.getBalance().add(request.getAmount()));

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        Transaction transaction = Transaction.builder()
                .type(TransactionType.TRANSFER)
                .amount(request.getAmount())
                .sourceAccount(sourceAccount)
                .destinationAccount(destinationAccount)
                .description(request.getDescription())
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransferResponse.builder()
                .transactionId(savedTransaction.getId())
                .sourceAccountId(sourceAccount.getId())
                .destinationAccountId(destinationAccount.getId())
                .amount(savedTransaction.getAmount())
                .message("Transfer completed successfully")
                .createdAt(savedTransaction.getCreatedAt())
                .build();
    }

    public TransactionResponse getTransactionById(Long id) {
        return null;
    }

    public List<TransactionResponse> getAccountHistory(Long accountId) {
        return null;
    }
}
