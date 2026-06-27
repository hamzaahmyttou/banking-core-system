package com.banking_core_system.banking_core_system.customer.mapper;

import com.banking_core_system.banking_core_system.customer.dto.CustomerResponse;
import com.banking_core_system.banking_core_system.customer.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerResponse toResponse(Customer customer) {

        if (customer == null) {
            return null;
        }

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
