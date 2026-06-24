package com.banking_core_system.banking_core_system.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "CIN is required")
    @Pattern(
            regexp = "^[A-Z]{1,2}[0-9]{4,8}$",
            message = "Invalid CIN format"
    )
    private String cin;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;

    @Pattern(
            regexp = "^[0-9+ ]*$",
            message = "Invalid phone number"
    )
    private String phoneNumber;
}
