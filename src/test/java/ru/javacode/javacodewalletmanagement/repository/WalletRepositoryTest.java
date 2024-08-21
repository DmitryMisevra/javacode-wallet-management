package ru.javacode.javacodewalletmanagement.repository;

import jakarta.persistence.LockModeType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import ru.javacode.javacodewalletmanagement.model.Wallet;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@ActiveProfiles("test")
class WalletRepositoryTest {

    @Autowired
    private WalletRepository walletRepository;

    private UUID walletId;
    private Wallet wallet;

    @BeforeEach
    void setUp() {
        walletId = UUID.randomUUID();
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
    void findByIdFroUpdate_whenWalletExists_thenReturnWallet() {
        Optional<Wallet> optionalWallet = walletRepository.findByIdForUpdate(walletId);
        assertTrue(optionalWallet.isPresent());
        assertEquals(walletId, optionalWallet.get().getId());
        assertEquals(wallet, optionalWallet.get());
    }
}