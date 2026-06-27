package com.banking_core_system.banking_core_system.account.controller;

import com.banking_core_system.banking_core_system.account.dto.AccountRequest;
import com.banking_core_system.banking_core_system.account.dto.AccountResponse;
import com.banking_core_system.banking_core_system.account.dto.BalanceResponse;
import com.banking_core_system.banking_core_system.account.entity.AccountStatus;
import com.banking_core_system.banking_core_system.account.entity.AccountType;
import com.banking_core_system.banking_core_system.account.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @Test
    void shouldCreateAccount() throws Exception {

        AccountRequest request = AccountRequest.builder()
                .customerId(1L)
                .type(AccountType.CHECKING)
                .build();

        AccountResponse response = AccountResponse.builder()
                .id(1L)
                .iban("MA64BK000000000000000001")
                .balance(BigDecimal.ZERO)
                .type(AccountType.CHECKING)
                .status(AccountStatus.ACTIVE)
                .customerId(1L)
                .customerName("Ahmed Alaoui")
                .createdAt(LocalDateTime.now())
                .build();

        when(accountService.createAccount(any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.iban").value("MA64BK000000000000000001"));
    }

    @Test
    void shouldReturnAccountById() throws Exception {

        AccountResponse response = AccountResponse.builder()
                .id(1L)
                .iban("MA64BK000000000000000001")
                .balance(BigDecimal.ZERO)
                .type(AccountType.CHECKING)
                .status(AccountStatus.ACTIVE)
                .customerId(1L)
                .customerName("Ahmed Alaoui")
                .build();

        when(accountService.getAccountById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerName")
                        .value("Ahmed Alaoui"));
    }

    @Test
    void shouldReturnAllAccounts() throws Exception {

        AccountResponse account1 = AccountResponse.builder()
                .id(1L)
                .build();

        AccountResponse account2 = AccountResponse.builder()
                .id(2L)
                .build();

        when(accountService.getAllAccounts())
                .thenReturn(List.of(account1, account2));

        mockMvc.perform(get("/api/v1/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnCustomerAccounts() throws Exception {

        AccountResponse account = AccountResponse.builder()
                .id(1L)
                .customerId(1L)
                .build();

        when(accountService.getCustomerAccounts(1L))
                .thenReturn(List.of(account));

        mockMvc.perform(get("/api/v1/accounts/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void shouldReturnBalance() throws Exception {

        BalanceResponse response = BalanceResponse.builder()
                .accountId(1L)
                .iban("MA64BK000000000000000001")
                .balance(BigDecimal.valueOf(1500))
                .lastUpdated(LocalDateTime.now())
                .build();

        when(accountService.getBalance(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/accounts/1/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(1500));
    }

    @Test
    void shouldCloseAccount() throws Exception {

        doNothing().when(accountService)
                .closeAccount(anyLong());

        mockMvc.perform(patch("/api/v1/accounts/1/close"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnBadRequestWhenCustomerIdIsMissing()
            throws Exception {

        AccountRequest request = AccountRequest.builder()
                .type(AccountType.CHECKING)
                .build();

        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
