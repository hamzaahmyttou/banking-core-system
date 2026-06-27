package com.banking_core_system.banking_core_system.account.dto;

import com.banking_core_system.banking_core_system.account.entity.AccountStatus;
import com.banking_core_system.banking_core_system.account.entity.AccountType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record AccountResponse(

        Long id,

        String iban,

        BigDecimal balance,

        AccountType type,

        AccountStatus status,

        Long customerId,

        String customerName,

        LocalDateTime createdAt

) {
}
