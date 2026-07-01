package com.banking_core_system.banking_core_system.transaction.controller;

import com.banking_core_system.banking_core_system.exception.AccountNotFoundException;
import com.banking_core_system.banking_core_system.exception.TransactionNotFoundException;
import com.banking_core_system.banking_core_system.transaction.dto.*;
import com.banking_core_system.banking_core_system.transaction.entity.TransactionType;
import com.banking_core_system.banking_core_system.transaction.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    @Test
    void shouldDepositSuccessfully() throws Exception {

        DepositRequest request = DepositRequest.builder()
                .accountId(1L)
                .amount(BigDecimal.valueOf(500))
                .description("Deposit")
                .build();

        TransactionResponse response = TransactionResponse.builder()
                .id(1L)
                .type(TransactionType.DEPOSIT)
                .amount(BigDecimal.valueOf(500))
                .destinationAccountId(1L)
                .description("Deposit")
                .build();

        when(transactionService.deposit(any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/transactions/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("DEPOSIT"));
    }

    @Test
    void shouldReturnBadRequestWhenDepositRequestIsInvalid() throws Exception {

        DepositRequest request = DepositRequest.builder()
                .accountId(null)
                .amount(BigDecimal.ZERO)
                .build();

        mockMvc.perform(post("/api/v1/transactions/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(transactionService);
    }

    @Test
    void shouldWithdrawSuccessfully() throws Exception {

        WithdrawalRequest request = WithdrawalRequest.builder()
                .accountId(1L)
                .amount(BigDecimal.valueOf(200))
                .build();

        TransactionResponse response = TransactionResponse.builder()
                .id(1L)
                .type(TransactionType.WITHDRAWAL)
                .amount(BigDecimal.valueOf(200))
                .sourceAccountId(1L)
                .build();

        when(transactionService.withdraw(any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/transactions/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("WITHDRAWAL"));
    }

    @Test
    void shouldReturnBadRequestWhenWithdrawalRequestIsInvalid() throws Exception {

        WithdrawalRequest request = WithdrawalRequest.builder()
                .accountId(null)
                .amount(BigDecimal.ZERO)
                .build();

        mockMvc.perform(post("/api/v1/transactions/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(transactionService);
    }

    @Test
    void shouldTransferSuccessfully() throws Exception {

        TransferRequest request = TransferRequest.builder()
                .sourceAccountId(1L)
                .destinationAccountId(2L)
                .amount(BigDecimal.valueOf(300))
                .build();

        TransferResponse response = TransferResponse.builder()
                .transactionId(10L)
                .sourceAccountId(1L)
                .destinationAccountId(2L)
                .amount(BigDecimal.valueOf(300))
                .message("Transfer completed successfully")
                .build();

        when(transactionService.transfer(any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/transactions/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionId").value(10));
    }

    @Test
    void shouldReturnBadRequestWhenTransferRequestIsInvalid() throws Exception {

        TransferRequest request = TransferRequest.builder()
                .sourceAccountId(null)
                .destinationAccountId(null)
                .amount(BigDecimal.ZERO)
                .build();

        mockMvc.perform(post("/api/v1/transactions/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(transactionService);
    }

    @Test
    void shouldReturnTransactionById() throws Exception {

        TransactionResponse response = TransactionResponse.builder()
                .id(1L)
                .type(TransactionType.DEPOSIT)
                .amount(BigDecimal.valueOf(500))
                .build();

        when(transactionService.getTransactionById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/transactions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturnNotFoundWhenTransactionDoesNotExist() throws Exception {

        when(transactionService.getTransactionById(99L))
                .thenThrow(new TransactionNotFoundException(
                        "Transaction with id 99 not found"));

        mockMvc.perform(get("/api/v1/transactions/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnAccountHistory() throws Exception {

        TransactionResponse response = TransactionResponse.builder()
                .id(1L)
                .type(TransactionType.DEPOSIT)
                .amount(BigDecimal.valueOf(500))
                .build();

        when(transactionService.getAccountHistory(1L))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/transactions/account/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void shouldReturnNotFoundWhenAccountDoesNotExist() throws Exception {

        when(transactionService.getAccountHistory(99L))
                .thenThrow(new AccountNotFoundException(
                        "Account with id 99 not found"));

        mockMvc.perform(get("/api/v1/transactions/account/99"))
                .andExpect(status().isNotFound());
    }
}
