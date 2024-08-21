package ru.javacode.javacodewalletmanagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * UpdateWalletDto is transferred in the user's request to change the balance
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWalletDto {


    @NotNull(message = "id is not specified")
    private UUID walletId;

    @NotNull(message = "Amount is not specified")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "The balance operation is not provided")
    private WalletOperation operation;
}
