package com.banking_core_system.banking_core_system.transaction.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record TransferResponse(

        Long transactionId,

        Long sourceAccountId,

        Long destinationAccountId,

        BigDecimal amount,

        String message,

        LocalDateTime createdAt

) {
}
