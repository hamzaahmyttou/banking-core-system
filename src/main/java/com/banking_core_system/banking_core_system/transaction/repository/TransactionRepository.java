package com.banking_core_system.banking_core_system.transaction.repository;

import com.banking_core_system.banking_core_system.account.entity.Account;
import com.banking_core_system.banking_core_system.transaction.entity.Transaction;
import com.banking_core_system.banking_core_system.transaction.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySourceAccountOrDestinationAccount(
            Account sourceAccount,
            Account destinationAccount
    );

    List<Transaction> findByType(TransactionType type);

    List<Transaction> findByCreatedAtBetween(
            LocalDateTime start,
            LocalDateTime end
    );

    List<Transaction> findBySourceAccountOrDestinationAccountOrderByCreatedAtDesc(
            Account sourceAccount,
            Account destinationAccount
    );
}
