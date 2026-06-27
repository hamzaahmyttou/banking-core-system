package com.banking_core_system.banking_core_system.account.service;

import com.banking_core_system.banking_core_system.account.dto.AccountRequest;
import com.banking_core_system.banking_core_system.account.dto.AccountResponse;
import com.banking_core_system.banking_core_system.account.dto.BalanceResponse;

import java.util.List;

public interface AccountService {

    AccountResponse createAccount(AccountRequest request);

    AccountResponse getAccountById(Long id);

    BalanceResponse getBalance(Long accountId);

    List<AccountResponse> getAllAccounts();

    List<AccountResponse> getCustomerAccounts(Long customerId);

    void closeAccount(Long id);
}
