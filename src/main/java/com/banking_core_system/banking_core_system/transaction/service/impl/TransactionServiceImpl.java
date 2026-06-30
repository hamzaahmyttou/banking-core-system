package com.banking_core_system.banking_core_system.transaction.service.impl;

import com.banking_core_system.banking_core_system.account.entity.Account;
import com.banking_core_system.banking_core_system.account.entity.AccountStatus;
import com.banking_core_system.banking_core_system.account.repository.AccountRepository;
import com.banking_core_system.banking_core_system.exception.AccountNotFoundException;
import com.banking_core_system.banking_core_system.exception.InvalidAccountStatusException;
import com.banking_core_system.banking_core_system.transaction.dto.*;
import com.banking_core_system.banking_core_system.transaction.entity.Transaction;
import com.banking_core_system.banking_core_system.transaction.entity.TransactionType;
import com.banking_core_system.banking_core_system.transaction.mapper.TransactionMapper;
import com.banking_core_system.banking_core_system.transaction.repository.TransactionRepository;
import com.banking_core_system.banking_core_system.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public TransactionResponse withdraw(WithdrawalRequest request) {
        return null;
    }

    public TransferResponse transfer(TransferRequest request) {
        return null;
    }

    public TransactionResponse getTransactionById(Long id) {
        return null;
    }

    public List<TransactionResponse> getAccountHistory(Long accountId) {
        return null;
    }
}
