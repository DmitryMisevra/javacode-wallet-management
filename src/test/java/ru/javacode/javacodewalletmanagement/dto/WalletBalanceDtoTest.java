package ru.javacode.javacodewalletmanagement.dto;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
class WalletBalanceDtoTest {

    @Autowired
    private JacksonTester<WalletBalanceDto> json;

    @SneakyThrows
    @Test
    void WalletBalanceDto_SerializationTest() {
        WalletBalanceDto walletBalanceDto = WalletBalanceDto.builder()
                .walletId(UUID.fromString("b5d23e1f-5e5d-4db9-9cf8-821be7a8a5b7"))
                .amount(new BigDecimal(2000))
                .build();

        JsonContent<WalletBalanceDto> result = json.write(walletBalanceDto);

        assertThat(result).extractingJsonPathStringValue("$.walletId")
                .isEqualTo(walletBalanceDto.getWalletId().toString());
        assertThat(result).extractingJsonPathNumberValue("$.amount")
                .isEqualTo(walletBalanceDto.getAmount().intValue());
    }
}