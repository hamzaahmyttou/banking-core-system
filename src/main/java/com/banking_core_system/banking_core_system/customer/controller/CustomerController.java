package com.banking_core_system.banking_core_system.customer.controller;

import com.banking_core_system.banking_core_system.customer.dto.CustomerRequest;
import com.banking_core_system.banking_core_system.customer.dto.CustomerResponse;
import com.banking_core_system.banking_core_system.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CustomerRequest request) {

        CustomerResponse customer = customerService.createCustomer(request);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(
            @PathVariable Long id) {

        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {

        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest request) {

        return ResponseEntity.ok(customerService.updateCustomer(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable Long id) {

        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
