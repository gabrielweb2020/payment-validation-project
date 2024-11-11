package com.example.paymentValidation.web.controllers;

import com.example.paymentValidation.application.usecases.ValidateAndProcessPaymentUseCaseImpl;
import com.example.paymentValidation.domain.entities.Payment;
import com.example.paymentValidation.web.dto.PaymentRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para operações de pagamento, incluindo o processamento e a listagem de pagamentos.
 */
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final ValidateAndProcessPaymentUseCaseImpl validateAndProcessPaymentUseCase;
    private final ModelMapper modelMapper;

    /**
     * Construtor para injetar dependências do caso de uso e do ModelMapper.
     *
     * @param validateAndProcessPaymentUseCase caso de uso para validação e processamento de pagamentos.
     * @param modelMapper mapeador para converter DTOs em entidades.
     */
    public PaymentController(ValidateAndProcessPaymentUseCaseImpl validateAndProcessPaymentUseCase,
                             ModelMapper modelMapper) {
        this.validateAndProcessPaymentUseCase = validateAndProcessPaymentUseCase;
        this.modelMapper = modelMapper;
    }

    /**
     * Endpoint para processar uma lista de pagamentos para um vendedor específico.
     *
     * @param sellerCode o código do vendedor.
     * @param payments lista de pagamentos a serem processados.
     * @return uma resposta de sucesso se os pagamentos forem processados corretamente.
     */
    @PostMapping("/process")
    public ResponseEntity<String> processPayments(@RequestParam String sellerCode,
                                                  @RequestBody List<PaymentRequestDTO> payments) {
        List<Payment> paymentList = payments.stream()
                .map(dto -> modelMapper.map(dto, Payment.class))
                .collect(Collectors.toList());

        validateAndProcessPaymentUseCase.execute(sellerCode, paymentList);

        return ResponseEntity.status(HttpStatus.OK).body("Pagamentos processados com sucesso");
    }

    /**
     * Endpoint para listar pagamentos com paginação.
     *
     * @param pageable objeto Pageable para controle da paginação.
     * @return uma página contendo os pagamentos listados.
     */
    @GetMapping("/list")
    public ResponseEntity<Page<Payment>> getPayments(Pageable pageable) {
        Page<Payment> paymentPage = validateAndProcessPaymentUseCase.getPayments(pageable);
        return ResponseEntity.ok(paymentPage);
    }
}



