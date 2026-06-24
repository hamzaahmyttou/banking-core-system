package com.banking_core_system.banking_core_system.customer.controller;

import com.banking_core_system.banking_core_system.customer.dto.CustomerRequest;
import com.banking_core_system.banking_core_system.customer.dto.CustomerResponse;
import com.banking_core_system.banking_core_system.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(
            @Valid @RequestBody CustomerRequest request) {

        return customerService.createCustomer(request);
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(
            @PathVariable Long id) {

        return customerService.getCustomerById(id);
    }

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {

        return customerService.getAllCustomers();
    }

    @PutMapping("/{id}")
    public CustomerResponse updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest request) {

        return customerService.updateCustomer(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(
            @PathVariable Long id) {

        customerService.deleteCustomer(id);
    }
}
