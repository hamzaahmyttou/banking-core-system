package com.banking_core_system.banking_core_system.transaction.service.impl;

import com.banking_core_system.banking_core_system.account.repository.AccountRepository;
import com.banking_core_system.banking_core_system.transaction.dto.*;
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

    public TransactionResponse deposit(DepositRequest request) {
        return null;
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
