package com.banking_core_system.banking_core_system.transaction.controller;

import com.banking_core_system.banking_core_system.transaction.dto.*;
import com.banking_core_system.banking_core_system.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(
            @Valid @RequestBody DepositRequest request) {

        return new ResponseEntity<>(
                transactionService.deposit(request),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(
            @Valid @RequestBody WithdrawalRequest request) {

        return new ResponseEntity<>(
                transactionService.withdraw(request),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(
            @Valid @RequestBody TransferRequest request) {

        return new ResponseEntity<>(
                transactionService.transfer(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                transactionService.getTransactionById(id)
        );
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponse>> getAccountHistory(
            @PathVariable Long accountId) {

        return ResponseEntity.ok(
                transactionService.getAccountHistory(accountId)
        );
    }
}
