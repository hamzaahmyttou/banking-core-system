package com.banking_core_system.banking_core_system.account.mapper;

import com.banking_core_system.banking_core_system.account.dto.AccountResponse;
import com.banking_core_system.banking_core_system.account.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountResponse toResponse(Account account) {

        if (account == null) {
            return null;
        }

        return AccountResponse.builder()
                .id(account.getId())
                .iban(account.getIban())
                .balance(account.getBalance())
                .type(account.getType())
                .status(account.getStatus())
                .customerId(account.getCustomer().getId())
                .customerName(
                        account.getCustomer().getFirstName()
                                + " "
                                + account.getCustomer().getLastName()
                )
                .createdAt(account.getCreatedAt())
                .build();
    }
}
