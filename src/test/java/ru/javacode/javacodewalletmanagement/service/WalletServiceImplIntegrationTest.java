package ru.javacode.javacodewalletmanagement.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.javacode.javacodewalletmanagement.dto.UpdateWalletDto;
import ru.javacode.javacodewalletmanagement.dto.WalletBalanceDto;
import ru.javacode.javacodewalletmanagement.dto.WalletOperation;
import ru.javacode.javacodewalletmanagement.exception.InsufficientFundsException;
import ru.javacode.javacodewalletmanagement.exception.NotFoundException;
import ru.javacode.javacodewalletmanagement.mapper.WalletMapper;
import ru.javacode.javacodewalletmanagement.model.Wallet;
import ru.javacode.javacodewalletmanagement.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class WalletServiceImplIntegrationTest {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletServiceImpl walletService;

    private Wallet wallet;

    @BeforeEach
    void setUp() {
        // Инициализация тестового кошелька
        UUID walletId = UUID.randomUUID();
        wallet = Wallet.builder()
                .id(walletId)
                .amount(BigDecimal.valueOf(100))
                .build();
        walletRepository.save(wallet);
    }

    @AfterEach
    void tearDown() {
        walletRepository.delete(wallet);
    }

    @Test
    void updateWalletBalance_whenDepositSuccess_ReturnWalletBalance() {
        UpdateWalletDto updateWalletDto = UpdateWalletDto.builder()
                .walletId(wallet.getId())
                .amount(BigDecimal.valueOf(500))
                .operation(WalletOperation.DEPOSIT)
                .build();

        WalletBalanceDto updatedWallet = walletService.updateWalletBalance(updateWalletDto);

        assertEquals(BigDecimal.valueOf(600), updatedWallet.getAmount());
    }

    @Test
    void updateWalletBalance_withdrawSuccess_ReturnWalletBalance() {
        UpdateWalletDto updateWalletDto = UpdateWalletDto.builder()
                .walletId(wallet.getId())
                .amount(BigDecimal.valueOf(50))
                .operation(WalletOperation.WITHDRAW)
                .build();

        WalletBalanceDto updatedWallet = walletService.updateWalletBalance(updateWalletDto);

        assertEquals(BigDecimal.valueOf(50), updatedWallet.getAmount());
    }

    @Test
    void updateWalletBalance_whenInsufficientFunds_thenInsufficientFundsException() {
        UpdateWalletDto updateWalletDto = UpdateWalletDto.builder()
                .walletId(wallet.getId())
                .amount(BigDecimal.valueOf(2000))
                .operation(WalletOperation.WITHDRAW)
                .build();

        assertThrows(InsufficientFundsException.class, () -> walletService.updateWalletBalance(updateWalletDto));
    }

    @Test
    void getWalletBalanceByWalletId_WhenWalletExists_ReturnWalletBalance() {
        WalletBalanceDto walletBalance = walletService.getWalletBalanceByWalletId(wallet.getId());

        assertEquals(BigDecimal.valueOf(100), walletBalance.getAmount());
    }

    @Test
    void getWalletBalanceByWalletId_WhenWalletNotFound_thenThrowNotFoundException() {
        UUID nonExistentWalletId = UUID.randomUUID();
        assertThrows(NotFoundException.class, () -> walletService.getWalletBalanceByWalletId(nonExistentWalletId));
    }
}