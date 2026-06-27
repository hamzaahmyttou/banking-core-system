package com.banking_core_system.banking_core_system.account.controller;

import com.banking_core_system.banking_core_system.account.dto.AccountRequest;
import com.banking_core_system.banking_core_system.account.dto.AccountResponse;
import com.banking_core_system.banking_core_system.account.dto.BalanceResponse;
import com.banking_core_system.banking_core_system.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(
            @Valid @RequestBody AccountRequest request) {

        return accountService.createAccount(request);
    }

    @GetMapping("/{id}")
    public AccountResponse getAccountById(
            @PathVariable Long id) {

        return accountService.getAccountById(id);
    }

    @GetMapping
    public List<AccountResponse> getAllAccounts() {

        return accountService.getAllAccounts();
    }

    @GetMapping("/customer/{customerId}")
    public List<AccountResponse> getCustomerAccounts(
            @PathVariable Long customerId) {

        return accountService.getCustomerAccounts(customerId);
    }

    @GetMapping("/{id}/balance")
    public BalanceResponse getBalance(
            @PathVariable Long id) {

        return accountService.getBalance(id);
    }

    @PatchMapping("/{id}/close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeAccount(
            @PathVariable Long id) {

        accountService.closeAccount(id);
    }
}
