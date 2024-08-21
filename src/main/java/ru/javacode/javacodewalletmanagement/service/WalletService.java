package ru.javacode.javacodewalletmanagement.service;

import ru.javacode.javacodewalletmanagement.dto.UpdateWalletDto;
import ru.javacode.javacodewalletmanagement.dto.WalletBalanceDto;

import java.util.UUID;

public interface WalletService {

    WalletBalanceDto updateWalletBalance(UpdateWalletDto updateWalletDto);
    WalletBalanceDto getWalletBalanceByWalletId(UUID walletId);
}
