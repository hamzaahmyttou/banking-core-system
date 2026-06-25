package com.banking_core_system.banking_core_system.customer.service;

import com.banking_core_system.banking_core_system.customer.dto.CustomerRequest;
import com.banking_core_system.banking_core_system.customer.dto.CustomerResponse;
import com.banking_core_system.banking_core_system.customer.entity.Customer;
import com.banking_core_system.banking_core_system.customer.entity.CustomerStatus;
import com.banking_core_system.banking_core_system.customer.repository.CustomerRepository;
import com.banking_core_system.banking_core_system.customer.service.impl.CustomerServiceImpl;
import com.banking_core_system.banking_core_system.exception.CustomerAlreadyExistsException;
import com.banking_core_system.banking_core_system.exception.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void shouldCreateCustomerSuccessfully() {

        CustomerRequest request = CustomerRequest.builder()
                .firstName("Ahmed")
                .lastName("Alaoui")
                .cin("AB123456")
                .email("ahmed@gmail.com")
                .phoneNumber("+212612345678")
                .build();

        Customer customer = Customer.builder()
                .id(1L)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .cin(request.getCin())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .status(CustomerStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        when(customerRepository.existsByCin(request.getCin()))
                .thenReturn(false);

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customer);

        CustomerResponse response =
                customerService.createCustomer(request);

        assertNotNull(response);
        assertEquals("Ahmed", response.getFirstName());
        assertEquals("AB123456", response.getCin());

        verify(customerRepository, times(1))
                .save(any(Customer.class));
    }

    @Test
    void shouldThrowExceptionWhenCustomerAlreadyExists() {

        CustomerRequest request = CustomerRequest.builder()
                .cin("AB123456")
                .build();

        when(customerRepository.existsByCin("AB123456"))
                .thenReturn(true);

        assertThrows(
                CustomerAlreadyExistsException.class,
                () -> customerService.createCustomer(request)
        );

        verify(customerRepository, never())
                .save(any());
    }

    @Test
    void shouldReturnCustomerById() {

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("Ahmed")
                .lastName("Alaoui")
                .cin("AB123456")
                .email("ahmed@gmail.com")
                .status(CustomerStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        CustomerResponse response =
                customerService.getCustomerById(1L);

        assertEquals(1L, response.getId());
        assertEquals("Ahmed", response.getFirstName());
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {

        when(customerRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                CustomerNotFoundException.class,
                () -> customerService.getCustomerById(99L)
        );
    }

    @Test
    void shouldDeleteCustomerSuccessfully() {

        Customer customer = Customer.builder()
                .id(1L)
                .build();

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        customerService.deleteCustomer(1L);

        verify(customerRepository, times(1))
                .delete(customer);
    }
}
