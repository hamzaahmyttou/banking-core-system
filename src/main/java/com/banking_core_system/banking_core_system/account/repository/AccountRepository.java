package com.banking_core_system.banking_core_system.account.repository;

import com.banking_core_system.banking_core_system.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByIban(String iban);

    boolean existsByIban(String iban);

    List<Account> findByCustomerId(Long customerId);
}
