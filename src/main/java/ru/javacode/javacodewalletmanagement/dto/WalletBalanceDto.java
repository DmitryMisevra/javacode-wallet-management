package ru.javacode.javacodewalletmanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * WalletBalanceDto is returned in http-response when user requires wallet balance
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletBalanceDto {

    private UUID walletId;
    private BigDecimal amount;
}
