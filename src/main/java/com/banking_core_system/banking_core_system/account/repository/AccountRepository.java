package com.banking_core_system.banking_core_system.account.repository;

import com.banking_core_system.banking_core_system.account.entity.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByIban(String iban);

    boolean existsByIban(String iban);

    List<Account> findByCustomerId(Long customerId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findWithLockByIban(String iban);
}
