package com.example.paymentValidation.application.service;

import com.example.paymentValidation.domain.entities.Seller;
import com.example.paymentValidation.domain.repositories.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@EnableCaching
public class ValidationServiceTest {

    @Mock
    private SellerRepository sellerRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCacheForValidateSellerExists() {
        String sellerCode = "123";
        Seller seller = new Seller(sellerCode);
        when(sellerRepository.findBySellerCode(sellerCode)).thenReturn(Optional.of(seller));

        // Primeira chamada, deveria acessar o repositório
        Seller firstCallResult = validationService.validateSellerExists(sellerCode);
        assertNotNull(firstCallResult);
        verify(sellerRepository, times(1)).findBySellerCode(sellerCode);

        // Segunda chamada, deveria usar o cache
        Seller secondCallResult = validationService.validateSellerExists(sellerCode);
        assertNotNull(secondCallResult);
        verify(sellerRepository, times(1)).findBySellerCode(sellerCode);
    }
}
