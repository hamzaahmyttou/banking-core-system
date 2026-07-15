package com.banking_core_system.banking_core_system.account.controller;

import com.banking_core_system.banking_core_system.account.dto.AccountRequest;
import com.banking_core_system.banking_core_system.account.dto.AccountResponse;
import com.banking_core_system.banking_core_system.account.dto.BalanceResponse;
import com.banking_core_system.banking_core_system.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @Valid @RequestBody AccountRequest request) {

        return new ResponseEntity<>(accountService.createAccount(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(
            @PathVariable Long id) {

        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {

        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AccountResponse>> getCustomerAccounts(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(accountService.getCustomerAccounts(customerId));
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BalanceResponse> getBalance(
            @PathVariable Long id) {

        return ResponseEntity.ok(accountService.getBalance(id));
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<Void> closeAccount(
            @PathVariable Long id) {

        accountService.closeAccount(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
