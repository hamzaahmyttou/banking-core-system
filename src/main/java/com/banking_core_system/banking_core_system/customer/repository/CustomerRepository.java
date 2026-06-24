package com.banking_core_system.banking_core_system.customer.repository;

import com.banking_core_system.banking_core_system.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByCin(String cin);

    boolean existsByCin(String cin);

    boolean existsByEmail(String email);
}
