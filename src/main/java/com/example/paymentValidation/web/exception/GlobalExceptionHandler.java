package com.example.paymentValidation.web.exception;

import com.example.paymentValidation.domain.exceptions.PaymentNotFoundException;
import com.example.paymentValidation.domain.exceptions.SellerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Manipulador global de exceções para lidar com exceções específicas e genéricas na aplicação.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata exceções de vendedor não encontrado.
     *
     * @param ex exceção SellerNotFoundException.
     * @return uma resposta com status 404 e mensagem de erro.
     */
    @ExceptionHandler(SellerNotFoundException.class)
    public ResponseEntity<String> handleSellerNotFound(SellerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Trata exceções de pagamento não encontrado.
     *
     * @param ex exceção PaymentNotFoundException.
     * @return uma resposta com status 404 e mensagem de erro.
     */
    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<String> handlePaymentNotFound(PaymentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Trata exceções genéricas não específicas.
     *
     * @param ex exceção genérica.
     * @return uma resposta com status 500 e mensagem de erro.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
    }
}


