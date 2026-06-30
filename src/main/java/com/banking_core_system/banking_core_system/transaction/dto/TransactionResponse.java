package com.banking_core_system.banking_core_system.transaction.dto;

import com.banking_core_system.banking_core_system.transaction.entity.TransactionType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record TransactionResponse(

        Long id,

        TransactionType type,

        BigDecimal amount,

        Long sourceAccountId,

        Long destinationAccountId,

        String description,

        LocalDateTime createdAt

) {
}
