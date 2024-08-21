package ru.javacode.javacodewalletmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.javacode.javacodewalletmanagement.dto.UpdateWalletDto;
import ru.javacode.javacodewalletmanagement.dto.WalletBalanceDto;
import ru.javacode.javacodewalletmanagement.dto.WalletOperation;
import ru.javacode.javacodewalletmanagement.service.WalletService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WalletController.class)
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WalletService walletService;



    @SneakyThrows
    @Test
    void updateWalletBalance_whenSuccess_shouldReturnStatusOkWithWalletBalance() {
        UpdateWalletDto updateWalletDto = UpdateWalletDto.builder()
                .walletId(UUID.fromString("b5d23e1f-5e5d-4db9-9cf8-821be7a8a5b7"))
                .amount(new BigDecimal(1000))
                .operation(WalletOperation.DEPOSIT)
                .build();

        WalletBalanceDto walletBalanceDto = WalletBalanceDto.builder()
                .walletId(UUID.fromString("b5d23e1f-5e5d-4db9-9cf8-821be7a8a5b7"))
                .amount(new BigDecimal(2000))
                .build();

        Mockito.when(walletService.updateWalletBalance(Mockito.any())).thenReturn(walletBalanceDto);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateWalletDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(walletBalanceDto.getWalletId().toString()))
                .andExpect(jsonPath("$.amount").value(walletBalanceDto.getAmount().toString()));
    }

    @SneakyThrows
    @Test
    void updateWalletBalance_whenInvalidJsonWithNullId_shouldReturnStatusBadRequest() {
        UpdateWalletDto updateWalletDto = UpdateWalletDto.builder()
                .walletId(null)
                .amount(new BigDecimal(1000))
                .operation(WalletOperation.DEPOSIT)
                .build();

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateWalletDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("id is not specified"));
    }

    @SneakyThrows
    @Test
    void getWalletBalanceByWalletId_WhenSuccess_shouldReturnStatusOkWithWalletBalance() {
        String walletId = UUID.randomUUID().toString();

        WalletBalanceDto walletBalanceDto = WalletBalanceDto.builder()
                .walletId(UUID.fromString("b5d23e1f-5e5d-4db9-9cf8-821be7a8a5b7"))
                .amount(new BigDecimal(2000))
                .build();

        Mockito.when(walletService.getWalletBalanceByWalletId(Mockito.any())).thenReturn(walletBalanceDto);

        mockMvc.perform(get("/api/v1/wallets/{walletId}", walletId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(walletBalanceDto.getWalletId().toString()))
                .andExpect(jsonPath("$.amount").value(walletBalanceDto.getAmount().toString()));
    }

    @SneakyThrows
    @Test
    void getWalletBalanceByWalletId_WhenInvalidUUIDFormat_shouldReturnStatusBadRequest() {
        String walletId = "123";

        mockMvc.perform(get("/api/v1/wallets/{walletId}", walletId))
                .andExpect(status().isBadRequest());
    }
}