package com.banking_core_system.banking_core_system.customer.service;

import com.banking_core_system.banking_core_system.customer.dto.CustomerRequest;
import com.banking_core_system.banking_core_system.customer.dto.CustomerResponse;
import com.banking_core_system.banking_core_system.customer.entity.Customer;
import com.banking_core_system.banking_core_system.customer.entity.CustomerStatus;
import com.banking_core_system.banking_core_system.customer.mapper.CustomerMapper;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerMapper customerMapper;

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
                .firstName("Ahmed")
                .lastName("Alaoui")
                .cin("AB123456")
                .email("ahmed@gmail.com")
                .phoneNumber("+212612345678")
                .status(CustomerStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        CustomerResponse response = CustomerResponse.builder()
                .id(1L)
                .firstName("Ahmed")
                .lastName("Alaoui")
                .cin("AB123456")
                .email("ahmed@gmail.com")
                .phoneNumber("+212612345678")
                .status(CustomerStatus.ACTIVE)
                .createdAt(customer.getCreatedAt())
                .build();

        when(customerRepository.existsByCin(request.getCin()))
                .thenReturn(false);

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customer);

        when(customerMapper.toResponse(customer))
                .thenReturn(response);

        CustomerResponse result =
                customerService.createCustomer(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Ahmed", result.getFirstName());

        verify(customerRepository).save(any(Customer.class));
        verify(customerMapper).toResponse(customer);
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

        verify(customerRepository, never()).save(any());
        verifyNoInteractions(customerMapper);
    }

    @Test
    void shouldReturnCustomerById() {

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("Ahmed")
                .lastName("Alaoui")
                .build();

        CustomerResponse response = CustomerResponse.builder()
                .id(1L)
                .firstName("Ahmed")
                .lastName("Alaoui")
                .build();

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(customerMapper.toResponse(customer))
                .thenReturn(response);

        CustomerResponse result =
                customerService.getCustomerById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Ahmed", result.getFirstName());

        verify(customerMapper).toResponse(customer);
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {

        when(customerRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                CustomerNotFoundException.class,
                () -> customerService.getCustomerById(99L)
        );

        verifyNoInteractions(customerMapper);
    }

    @Test
    void shouldDeleteCustomerSuccessfully() {

        Customer customer = Customer.builder()
                .id(1L)
                .build();

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        customerService.deleteCustomer(1L);

        verify(customerRepository).delete(customer);
    }

    @Test
    void shouldReturnAllCustomers() {

        Customer customer1 = Customer.builder()
                .id(1L)
                .firstName("Ahmed")
                .lastName("Alaoui")
                .build();

        Customer customer2 = Customer.builder()
                .id(2L)
                .firstName("Sara")
                .lastName("Bennani")
                .build();

        CustomerResponse response1 = CustomerResponse.builder()
                .id(1L)
                .firstName("Ahmed")
                .lastName("Alaoui")
                .build();

        CustomerResponse response2 = CustomerResponse.builder()
                .id(2L)
                .firstName("Sara")
                .lastName("Bennani")
                .build();

        when(customerRepository.findAll())
                .thenReturn(List.of(customer1, customer2));

        when(customerMapper.toResponse(customer1))
                .thenReturn(response1);

        when(customerMapper.toResponse(customer2))
                .thenReturn(response2);

        List<CustomerResponse> result = customerService.getAllCustomers();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("Ahmed", result.get(0).getFirstName());
        assertEquals("Sara", result.get(1).getFirstName());

        verify(customerRepository).findAll();
        verify(customerMapper).toResponse(customer1);
        verify(customerMapper).toResponse(customer2);
    }

    @Test
    void shouldUpdateCustomerSuccessfully() {

        CustomerRequest request = CustomerRequest.builder()
                .firstName("Ahmed Updated")
                .lastName("Alaoui Updated")
                .cin("AB123456")
                .email("updated@gmail.com")
                .phoneNumber("+212600000000")
                .build();

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("Ahmed")
                .lastName("Alaoui")
                .cin("AB123456")
                .email("ahmed@gmail.com")
                .phoneNumber("+212612345678")
                .status(CustomerStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        Customer updatedCustomer = Customer.builder()
                .id(1L)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .cin(customer.getCin())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .status(CustomerStatus.ACTIVE)
                .createdAt(customer.getCreatedAt())
                .build();

        CustomerResponse response = CustomerResponse.builder()
                .id(1L)
                .firstName("Ahmed Updated")
                .lastName("Alaoui Updated")
                .cin("AB123456")
                .email("updated@gmail.com")
                .phoneNumber("+212600000000")
                .status(CustomerStatus.ACTIVE)
                .createdAt(customer.getCreatedAt())
                .build();

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(updatedCustomer);

        when(customerMapper.toResponse(updatedCustomer))
                .thenReturn(response);

        CustomerResponse result =
                customerService.updateCustomer(1L, request);

        assertNotNull(result);
        assertEquals("Ahmed Updated", result.getFirstName());
        assertEquals("updated@gmail.com", result.getEmail());

        verify(customerRepository).findById(1L);
        verify(customerRepository).save(any(Customer.class));
        verify(customerMapper).toResponse(updatedCustomer);
    }
}
