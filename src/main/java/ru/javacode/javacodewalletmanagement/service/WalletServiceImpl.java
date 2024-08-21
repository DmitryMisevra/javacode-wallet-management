package ru.javacode.javacodewalletmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javacode.javacodewalletmanagement.dto.UpdateWalletDto;
import ru.javacode.javacodewalletmanagement.dto.WalletBalanceDto;
import ru.javacode.javacodewalletmanagement.exception.InsufficientFundsException;
import ru.javacode.javacodewalletmanagement.exception.NotFoundException;
import ru.javacode.javacodewalletmanagement.mapper.WalletMapper;
import ru.javacode.javacodewalletmanagement.model.Wallet;
import ru.javacode.javacodewalletmanagement.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    @Override
    @Transactional
    public WalletBalanceDto updateWalletBalance(UpdateWalletDto updateWalletDto) {
        Wallet wallet = walletRepository.findByIdForUpdate(updateWalletDto.getWalletId()).orElseThrow(()
                -> new NotFoundException(
                "Wallet with id: " + updateWalletDto.getWalletId() + " not found"));

        BigDecimal walletAmount = wallet.getAmount();
        switch (updateWalletDto.getOperation()) {
            case DEPOSIT:
                walletAmount = walletAmount.add(updateWalletDto.getAmount());
                break;
            case WITHDRAW:
                if (walletAmount.compareTo(updateWalletDto.getAmount()) >= 0) {
                    walletAmount = walletAmount.subtract(updateWalletDto.getAmount());
                    break;
                } else {
                    throw new InsufficientFundsException("Insufficient funds in wallet with id: "
                            + updateWalletDto.getWalletId());
                }
        }
        wallet.setAmount(walletAmount);
        walletRepository.save(wallet);

        return walletMapper.WalletToWalletBalanceDto(wallet);
    }

    @Override
    @Transactional(readOnly = true)
    public WalletBalanceDto getWalletBalanceByWalletId(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new NotFoundException(
                "Wallet with id: " + walletId + " not found"));
        return walletMapper.WalletToWalletBalanceDto(wallet);
    }
}
