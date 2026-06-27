package com.banking_core_system.banking_core_system.customer.service.impl;

import com.banking_core_system.banking_core_system.customer.dto.CustomerRequest;
import com.banking_core_system.banking_core_system.customer.dto.CustomerResponse;
import com.banking_core_system.banking_core_system.customer.entity.Customer;
import com.banking_core_system.banking_core_system.customer.mapper.CustomerMapper;
import com.banking_core_system.banking_core_system.customer.repository.CustomerRepository;
import com.banking_core_system.banking_core_system.customer.service.CustomerService;
import com.banking_core_system.banking_core_system.exception.CustomerAlreadyExistsException;
import com.banking_core_system.banking_core_system.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {

        if (customerRepository.existsByCin(request.getCin())) {
            throw new CustomerAlreadyExistsException(
                    "Customer with CIN " + request.getCin() + " already exists"
            );
        }

        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .cin(request.getCin())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.toResponse(savedCustomer);
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                "Customer with id " + id + " not found"));

        return customerMapper.toResponse(customer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                "Customer with id " + id + " not found"));

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());

        Customer updatedCustomer = customerRepository.save(customer);

        return customerMapper.toResponse(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                "Customer with id " + id + " not found"));

        customerRepository.delete(customer);
    }
}
