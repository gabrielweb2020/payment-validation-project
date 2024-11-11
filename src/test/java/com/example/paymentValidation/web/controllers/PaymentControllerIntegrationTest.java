package com.example.paymentValidation.web.controllers;

import com.example.paymentValidation.web.dto.PaymentRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testes de integração para o controlador PaymentController.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PaymentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Testa o endpoint de processamento de pagamentos.
     */
    @Test
    public void testProcessPaymentsIntegration() throws Exception {
        PaymentRequestDTO paymentRequest = new PaymentRequestDTO();
        paymentRequest.setChargeCode("C1");
        paymentRequest.setAmount(new BigDecimal("100"));

        String jsonRequest = objectMapper.writeValueAsString(Collections.singletonList(paymentRequest));

        mockMvc.perform(post("/api/payments/process")
                        .param("sellerCode", "123")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string("Pagamentos processados com sucesso"));
    }
}
