package com.banking_core_system.banking_core_system.customer.dto;

import com.banking_core_system.banking_core_system.customer.entity.CustomerStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String cin;

    private String email;

    private String phoneNumber;

    private CustomerStatus status;

    private LocalDateTime createdAt;
}
