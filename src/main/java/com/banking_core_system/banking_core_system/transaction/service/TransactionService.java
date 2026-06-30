package com.banking_core_system.banking_core_system.transaction.service;

import com.banking_core_system.banking_core_system.transaction.dto.*;

import java.util.List;

public interface TransactionService {

    TransactionResponse deposit(DepositRequest request);

    TransactionResponse withdraw(WithdrawalRequest request);

    TransferResponse transfer(TransferRequest request);

    TransactionResponse getTransactionById(Long id);

    List<TransactionResponse> getAccountHistory(Long accountId);
}
