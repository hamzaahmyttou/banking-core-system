package com.banking_core_system.banking_core_system.customer.controller;

import com.banking_core_system.banking_core_system.customer.dto.CustomerRequest;
import com.banking_core_system.banking_core_system.customer.dto.CustomerResponse;
import com.banking_core_system.banking_core_system.customer.entity.CustomerStatus;
import com.banking_core_system.banking_core_system.customer.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @Test
    void shouldCreateCustomer() throws Exception {

        CustomerRequest request = CustomerRequest.builder()
                .firstName("Ahmed")
                .lastName("Alaoui")
                .cin("AB123456")
                .email("ahmed@gmail.com")
                .phoneNumber("+212612345678")
                .build();

        CustomerResponse response = CustomerResponse.builder()
                .id(1L)
                .firstName("Ahmed")
                .lastName("Alaoui")
                .cin("AB123456")
                .email("ahmed@gmail.com")
                .phoneNumber("+212612345678")
                .status(CustomerStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        when(customerService.createCustomer(any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Ahmed"))
                .andExpect(jsonPath("$.cin").value("AB123456"));
    }

    @Test
    void shouldReturnCustomerById() throws Exception {

        CustomerResponse response = CustomerResponse.builder()
                .id(1L)
                .firstName("Ahmed")
                .lastName("Alaoui")
                .cin("AB123456")
                .email("ahmed@gmail.com")
                .status(CustomerStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        when(customerService.getCustomerById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Ahmed"));
    }

    @Test
    void shouldReturnAllCustomers() throws Exception {

        CustomerResponse customer1 = CustomerResponse.builder()
                .id(1L)
                .firstName("Ahmed")
                .build();

        CustomerResponse customer2 = CustomerResponse.builder()
                .id(2L)
                .firstName("Sara")
                .build();

        when(customerService.getAllCustomers())
                .thenReturn(List.of(customer1, customer2));

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldUpdateCustomer() throws Exception {

        CustomerRequest request = CustomerRequest.builder()
                .firstName("Ahmed Updated")
                .lastName("Alaoui")
                .cin("AB123456")
                .email("updated@gmail.com")
                .build();

        CustomerResponse response = CustomerResponse.builder()
                .id(1L)
                .firstName("Ahmed Updated")
                .lastName("Alaoui")
                .cin("AB123456")
                .email("updated@gmail.com")
                .status(CustomerStatus.ACTIVE)
                .build();

        when(customerService.updateCustomer(any(Long.class), any()))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName")
                        .value("Ahmed Updated"));
    }

    @Test
    void shouldDeleteCustomer() throws Exception {

        doNothing().when(customerService)
                .deleteCustomer(1L);

        mockMvc.perform(delete("/api/v1/customers/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnBadRequestWhenFirstNameIsMissing()
            throws Exception {

        CustomerRequest request = CustomerRequest.builder()
                .lastName("Alaoui")
                .cin("AB123456")
                .email("ahmed@gmail.com")
                .build();

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
