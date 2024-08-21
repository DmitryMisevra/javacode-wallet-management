package ru.javacode.javacodewalletmanagement.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javacode.javacodewalletmanagement.dto.UpdateWalletDto;
import ru.javacode.javacodewalletmanagement.dto.WalletBalanceDto;
import ru.javacode.javacodewalletmanagement.service.WalletService;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
public class WalletController {

    private final WalletService walletService;

    /**
     * update balance (depend on deposit or withdraw)
     *
     * @param updateWalletDto updateWalletDto
     * @return walletBalanceDto
     */

    @PostMapping(path = "/wallet")
    ResponseEntity<WalletBalanceDto> updateWalletBalance(@Valid @RequestBody UpdateWalletDto updateWalletDto) {
        WalletBalanceDto walletBalanceDto = walletService.updateWalletBalance(updateWalletDto);
        log.debug("Wallet balance has been updated: {}", walletBalanceDto);
        return ResponseEntity.ok(walletBalanceDto);
    }


    /**
     * Getting balance of wallet by UUID
     *
     * @param walletId UUID
     * @return walletBalanceDto
     */

    @GetMapping(path = "/wallets/{walletId}")
    ResponseEntity<WalletBalanceDto> getWalletBalanceByWalletId(@PathVariable UUID walletId) {
        WalletBalanceDto walletBalanceDto = walletService.getWalletBalanceByWalletId(walletId);
        log.debug("Wallet balance has been received: {}", walletBalanceDto);
        return ResponseEntity.ok(walletBalanceDto);
    }
}
