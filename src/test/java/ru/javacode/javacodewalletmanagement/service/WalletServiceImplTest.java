package ru.javacode.javacodewalletmanagement.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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

@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private WalletMapper walletMapper;

    @InjectMocks
    private WalletServiceImpl walletService;

    @Test
    void updateWalletBalance_whenDepositSuccess_shouldUpdateWalletBalance() {
        UUID walletId = UUID.randomUUID();
        Wallet wallet = Wallet.builder()
                .id(walletId)
                .amount(BigDecimal.valueOf(100))
                .build();
        UpdateWalletDto updateWalletDto = UpdateWalletDto.builder()
                .walletId(walletId)
                .amount(BigDecimal.valueOf(50))
                .operation(WalletOperation.DEPOSIT)
                .build();
        WalletBalanceDto expectedDto = WalletBalanceDto.builder()
                .walletId(walletId)
                .amount(BigDecimal.valueOf(150))
                .build();


        Mockito.when(walletRepository.findByIdForUpdate(walletId)).thenReturn(Optional.of(wallet));
        Mockito.when(walletMapper.WalletToWalletBalanceDto(wallet)).thenReturn(expectedDto);


        WalletBalanceDto result = walletService.updateWalletBalance(updateWalletDto);

        assertEquals(BigDecimal.valueOf(150), result.getAmount());
        verify(walletRepository).save(wallet);
    }

    @Test
    void updateWalletBalance_whenWithdrawSuccess_shouldUpdateWalletBalance() {
        UUID walletId = UUID.randomUUID();
        Wallet wallet = Wallet.builder()
                .id(walletId)
                .amount(BigDecimal.valueOf(100))
                .build();
        UpdateWalletDto updateWalletDto = UpdateWalletDto.builder()
                .walletId(walletId)
                .amount(BigDecimal.valueOf(50))
                .operation(WalletOperation.WITHDRAW)
                .build();
        WalletBalanceDto expectedDto = WalletBalanceDto.builder()
                .walletId(walletId)
                .amount(BigDecimal.valueOf(50))
                .build();


        Mockito.when(walletRepository.findByIdForUpdate(walletId)).thenReturn(Optional.of(wallet));
        Mockito.when(walletMapper.WalletToWalletBalanceDto(wallet)).thenReturn(expectedDto);


        WalletBalanceDto result = walletService.updateWalletBalance(updateWalletDto);

        assertEquals(BigDecimal.valueOf(50), result.getAmount());
        verify(walletRepository).save(wallet);
    }

    @Test
    void updateWalletBalance_whenInsufficientFunds_shouldThrowException() {
        UUID walletId = UUID.randomUUID();
        Wallet wallet = Wallet.builder()
                .id(walletId)
                .amount(BigDecimal.valueOf(100))
                .build();
        UpdateWalletDto updateWalletDto = UpdateWalletDto.builder()
                .walletId(walletId)
                .amount(BigDecimal.valueOf(150))
                .operation(WalletOperation.WITHDRAW)
                .build();

        Mockito.when(walletRepository.findByIdForUpdate(walletId)).thenReturn(Optional.of(wallet));

        assertThrows(InsufficientFundsException.class, () -> {
            walletService.updateWalletBalance(updateWalletDto);
        });

        verify(walletRepository, Mockito.never()).save(Mockito.any(Wallet.class));
        verify(walletMapper, Mockito.never()).WalletToWalletBalanceDto(Mockito.any(Wallet.class));
    }

    @Test
    void updateWalletBalance_whenWalletNotFound_shouldThrowException() {
        UUID walletId = UUID.randomUUID();
        UpdateWalletDto updateWalletDto = UpdateWalletDto.builder()
                .walletId(walletId)
                .amount(BigDecimal.valueOf(150))
                .operation(WalletOperation.WITHDRAW)
                .build();

        Mockito.when(walletRepository.findByIdForUpdate(walletId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            walletService.updateWalletBalance(updateWalletDto);
        });

        verify(walletRepository, Mockito.never()).save(Mockito.any(Wallet.class));
        verify(walletMapper, Mockito.never()).WalletToWalletBalanceDto(Mockito.any(Wallet.class));
    }

    @Test
    void testGetWalletBalanceByWalletId_Success() {
        UUID walletId = UUID.randomUUID();
        Wallet wallet = Wallet.builder()
                .id(walletId)
                .amount(BigDecimal.valueOf(100))
                .build();

        Mockito.when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        WalletBalanceDto expectedDto = new WalletBalanceDto(walletId, BigDecimal.valueOf(100));
        Mockito.when(walletMapper.WalletToWalletBalanceDto(wallet)).thenReturn(expectedDto);

        WalletBalanceDto result = walletService.getWalletBalanceByWalletId(walletId);

        assertEquals(BigDecimal.valueOf(100), result.getAmount());
        verify(walletMapper).WalletToWalletBalanceDto(wallet);
    }

    @Test
    void testGetWalletBalanceByWalletId_WalletNotFound() {
        UUID walletId = UUID.randomUUID();

        Mockito.when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            walletService.getWalletBalanceByWalletId(walletId);
        });

        verify(walletMapper, Mockito.never()).WalletToWalletBalanceDto(Mockito.any(Wallet.class));
    }
}