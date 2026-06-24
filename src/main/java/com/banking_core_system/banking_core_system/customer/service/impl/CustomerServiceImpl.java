package com.banking_core_system.banking_core_system.customer.service.impl;

import com.banking_core_system.banking_core_system.customer.dto.CustomerRequest;
import com.banking_core_system.banking_core_system.customer.dto.CustomerResponse;
import com.banking_core_system.banking_core_system.customer.entity.Customer;
import com.banking_core_system.banking_core_system.customer.repository.CustomerRepository;
import com.banking_core_system.banking_core_system.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {

        if (customerRepository.existsByCin(request.getCin())) {
            throw new RuntimeException("Customer already exists");
        }

        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .cin(request.getCin())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        return mapToResponse(savedCustomer);
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return mapToResponse(customer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());

        Customer updatedCustomer = customerRepository.save(customer);

        return mapToResponse(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customerRepository.delete(customer);
    }

    private CustomerResponse mapToResponse(Customer customer) {

        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .cin(customer.getCin())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .status(customer.getStatus())
                .createdAt(customer.getCreatedAt())
                .build();
    }
}
