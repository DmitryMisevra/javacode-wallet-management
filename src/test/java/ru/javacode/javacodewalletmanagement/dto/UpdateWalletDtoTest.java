package ru.javacode.javacodewalletmanagement.dto;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JsonTest
class UpdateWalletDtoTest {

    @Autowired
    private JacksonTester<UpdateWalletDto> json;

    @SneakyThrows
    @Test
    void updatedWalletDto_DeserializationTest() {
        String content = "{\"walletId\": \"b5d23e1f-5e5d-4db9-9cf8-821be7a8a5b7\"," +
                " \"amount\": \"1000.00\", \"operation\": \"WITHDRAW\"}";

        UpdateWalletDto dto = json.parseObject(content);
        assertThat(dto.getWalletId()).isEqualTo(UUID.fromString("b5d23e1f-5e5d-4db9-9cf8-821be7a8a5b7"));
        assertThat(dto.getAmount()).isEqualTo(new BigDecimal("1000.00"));
        assertThat(dto.getOperation()).isEqualTo(WalletOperation.WITHDRAW);
    }
}