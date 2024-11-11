package com.example.paymentValidation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class PaymentValidationProjectApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	void contextLoads() {
		// Verifica se o contexto do Spring foi carregado corretamente
		assertNotNull(context, "O contexto da aplicação não foi carregado");
	}
}