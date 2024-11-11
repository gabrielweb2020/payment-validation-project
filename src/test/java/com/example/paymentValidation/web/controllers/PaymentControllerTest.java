package com.example.paymentValidation.web.controllers;

import com.example.paymentValidation.application.usecases.ValidateAndProcessPaymentUseCaseImpl;
import com.example.paymentValidation.domain.entities.Payment;
import com.example.paymentValidation.web.dto.PaymentRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Teste de integração para o controlador {@link PaymentController}.
 * Verifica o endpoint de processamento de pagamentos e garante que ele funcione
 * conforme o esperado, com os dados corretos e validação de entrada.
 */
@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ValidateAndProcessPaymentUseCaseImpl validateAndProcessPaymentUseCase;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private PaymentRequestDTO paymentRequestDTO;
    private Payment payment;

    /**
     * Configura os objetos de teste antes de cada execução.
     * Inicializa o PaymentRequestDTO e o objeto Payment, além de configurar
     * o comportamento do ModelMapper para conversão de DTO para entidade.
     */
    @BeforeEach
    public void setup() {
        // Inicializa um DTO de pagamento e um pagamento mapeado para o teste
        paymentRequestDTO = new PaymentRequestDTO();
        paymentRequestDTO.setChargeCode("C1");
        paymentRequestDTO.setAmount(new BigDecimal("100"));

        payment = new Payment("C1", new BigDecimal("100"));

        // Configura o comportamento do ModelMapper
        when(modelMapper.map(paymentRequestDTO, Payment.class)).thenReturn(payment);
    }

    /**
     * Testa o endpoint de processamento de pagamentos com uma entrada válida.
     * Verifica se o endpoint retorna status OK (200) e uma mensagem de sucesso.
     *
     * @throws Exception em caso de erro durante a execução do teste
     */
    @Test
    public void testProcessPayments_shouldReturnOk() throws Exception {
        // Configura o JSON da requisição
        String jsonRequest = objectMapper.writeValueAsString(Collections.singletonList(paymentRequestDTO));

        // Executa a requisição POST e verifica a resposta
        mockMvc.perform(post("/api/payments/process")
                        .param("sellerCode", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string("Pagamentos processados com sucesso"));

        // Verifica se o caso de uso foi chamado com o código do vendedor e a lista de pagamentos
        verify(validateAndProcessPaymentUseCase, times(1)).execute(eq("123"), anyList());
    }

    /**
     * Testa o endpoint de processamento de pagamentos com uma entrada inválida.
     * Verifica se o endpoint retorna status Bad Request (400) quando o campo sellerCode está vazio.
     *
     * @throws Exception em caso de erro durante a execução do teste
     */
    @Test
    public void testProcessPayments_shouldReturnBadRequestForInvalidInput() throws Exception {
        // Envia uma lista vazia para simular uma requisição inválida
        mockMvc.perform(post("/api/payments/process")
                        .param("sellerCode", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(status().isBadRequest());
    }
}
