package com.banking_core_system.banking_core_system.account.dto;

import com.banking_core_system.banking_core_system.account.entity.AccountType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequest {

    @NotNull
    private Long customerId;

    @NotNull
    private AccountType type;
}
