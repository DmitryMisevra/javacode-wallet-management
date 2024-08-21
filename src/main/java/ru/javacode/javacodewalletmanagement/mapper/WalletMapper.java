package ru.javacode.javacodewalletmanagement.mapper;

import org.springframework.stereotype.Component;
import ru.javacode.javacodewalletmanagement.dto.WalletBalanceDto;
import ru.javacode.javacodewalletmanagement.model.Wallet;


/**
 * WalletMapper for mapping entity Wallet and WalletBalanceDto
 */

@Component
public class WalletMapper {

    public WalletBalanceDto WalletToWalletBalanceDto(Wallet wallet) {
        return WalletBalanceDto.builder()
                .walletId(wallet.getId())
                .amount(wallet.getAmount())
                .build();
    }
}
