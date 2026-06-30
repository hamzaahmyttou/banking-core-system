package com.banking_core_system.banking_core_system.transaction.mapper;

import com.banking_core_system.banking_core_system.transaction.dto.TransactionResponse;
import com.banking_core_system.banking_core_system.transaction.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionResponse toResponse(Transaction transaction) {

        if (transaction == null) {
            return null;
        }

        return TransactionResponse.builder()
                .id(transaction.getId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .sourceAccountId(
                        transaction.getSourceAccount() != null
                                ? transaction.getSourceAccount().getId()
                                : null
                )
                .destinationAccountId(
                        transaction.getDestinationAccount() != null
                                ? transaction.getDestinationAccount().getId()
                                : null
                )
                .description(transaction.getDescription())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}
