package com.banking_core_system.banking_core_system.account.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record BalanceResponse(

        Long accountId,

        String iban,

        BigDecimal balance,

        LocalDateTime lastUpdated

) {
}
