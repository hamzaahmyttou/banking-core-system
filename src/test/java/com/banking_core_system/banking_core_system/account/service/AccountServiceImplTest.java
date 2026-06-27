package com.banking_core_system.banking_core_system.account.service;

import com.banking_core_system.banking_core_system.account.dto.AccountRequest;
import com.banking_core_system.banking_core_system.account.dto.AccountResponse;
import com.banking_core_system.banking_core_system.account.entity.Account;
import com.banking_core_system.banking_core_system.account.entity.AccountStatus;
import com.banking_core_system.banking_core_system.account.entity.AccountType;
import com.banking_core_system.banking_core_system.account.mapper.AccountMapper;
import com.banking_core_system.banking_core_system.account.repository.AccountRepository;
import com.banking_core_system.banking_core_system.account.service.impl.AccountServiceImpl;
import com.banking_core_system.banking_core_system.customer.entity.Customer;
import com.banking_core_system.banking_core_system.customer.repository.CustomerRepository;
import com.banking_core_system.banking_core_system.exception.AccountNotFoundException;
import com.banking_core_system.banking_core_system.exception.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void shouldCreateAccountSuccessfully() {

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("Ahmed")
                .lastName("Alaoui")
                .build();

        AccountRequest request = AccountRequest.builder()
                .customerId(1L)
                .type(AccountType.CHECKING)
                .build();

        Account account = Account.builder()
                .id(1L)
                .iban("MA64BK000000000000000001")
                .balance(BigDecimal.ZERO)
                .status(AccountStatus.ACTIVE)
                .type(AccountType.CHECKING)
                .customer(customer)
                .createdAt(LocalDateTime.now())
                .build();

        AccountResponse response = AccountResponse.builder()
                .id(1L)
                .iban(account.getIban())
                .balance(BigDecimal.ZERO)
                .status(AccountStatus.ACTIVE)
                .type(AccountType.CHECKING)
                .customerId(1L)
                .customerName("Ahmed Alaoui")
                .createdAt(account.getCreatedAt())
                .build();

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(accountRepository.save(any(Account.class)))
                .thenReturn(account);

        when(accountMapper.toResponse(account))
                .thenReturn(response);

        try (MockedStatic<com.banking_core_system.banking_core_system.common.util.IbanGenerator> mocked =
                     mockStatic(com.banking_core_system.banking_core_system.common.util.IbanGenerator.class)) {

            mocked.when(com.banking_core_system.banking_core_system.common.util.IbanGenerator::generate)
                    .thenReturn("MA64BK000000000000000001");

            AccountResponse result = accountService.createAccount(request);

            assertNotNull(result);
            assertEquals(1L, result.id());
            assertEquals(AccountType.CHECKING, result.type());

            verify(accountRepository).save(any(Account.class));
        }
    }

    @Test
    void shouldThrowExceptionWhenCustomerDoesNotExist() {

        AccountRequest request = AccountRequest.builder()
                .customerId(99L)
                .type(AccountType.CHECKING)
                .build();

        when(customerRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                CustomerNotFoundException.class,
                () -> accountService.createAccount(request)
        );

        verify(accountRepository, never()).save(any());
    }

    @Test
    void shouldReturnAccountById() {

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("Ahmed")
                .lastName("Alaoui")
                .build();

        Account account = Account.builder()
                .id(1L)
                .customer(customer)
                .build();

        AccountResponse response = AccountResponse.builder()
                .id(1L)
                .customerId(1L)
                .customerName("Ahmed Alaoui")
                .build();

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(accountMapper.toResponse(account))
                .thenReturn(response);

        AccountResponse result = accountService.getAccountById(1L);

        assertEquals(1L, result.id());
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFound() {

        when(accountRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> accountService.getAccountById(1L)
        );
    }

    @Test
    void shouldReturnAllAccounts() {

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("Ahmed")
                .lastName("Alaoui")
                .build();

        Account account = Account.builder()
                .id(1L)
                .customer(customer)
                .build();

        AccountResponse response = AccountResponse.builder()
                .id(1L)
                .build();

        when(accountRepository.findAll())
                .thenReturn(List.of(account));

        when(accountMapper.toResponse(account))
                .thenReturn(response);

        List<AccountResponse> result =
                accountService.getAllAccounts();

        assertEquals(1, result.size());
    }

    @Test
    void shouldCloseAccount() {

        Account account = Account.builder()
                .id(1L)
                .status(AccountStatus.ACTIVE)
                .build();

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        accountService.closeAccount(1L);

        assertEquals(AccountStatus.CLOSED, account.getStatus());

        verify(accountRepository).save(account);
    }
}
