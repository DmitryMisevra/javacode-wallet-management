package ru.javacode.javacodewalletmanagement.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javacode.javacodewalletmanagement.dto.WalletBalanceDto;
import ru.javacode.javacodewalletmanagement.model.Wallet;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WalletMapperTest {

    private WalletMapper walletMapper;

    @BeforeEach
    void setUp() {
        walletMapper = new WalletMapper();
    }

    @Test
    void walletToWalletBalanceDto_ifSuccess_shouldReturnWalletBalanceDto() {
        Wallet wallet = Wallet.builder()
                .id(UUID.fromString("b5d23e1f-5e5d-4db9-9cf8-821be7a8a5b7"))
                .amount(new BigDecimal(2000))
                .build();


        WalletBalanceDto convertedDto = walletMapper.WalletToWalletBalanceDto(wallet);
        assertNotNull(convertedDto);
        assertEquals(convertedDto.getWalletId(), wallet.getId());
        assertEquals(convertedDto.getAmount(), wallet.getAmount());

    }
}