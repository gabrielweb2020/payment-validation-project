# Payment Validation Project

Este projeto consiste em uma aplicação Java Spring Boot que implementa um sistema de validação e processamento de pagamentos. A aplicação segue a arquitetura limpa e práticas de desenvolvimento orientadas a testes, com o objetivo de garantir robustez, manutenibilidade e escalabilidade.

## Objetivo da Aplicação

A aplicação foi projetada para validar e processar pagamentos de forma segura e eficiente. Ela permite:
1. **Validação da existência de vendedores e códigos de cobrança** - Garante que o pagamento está associado a um vendedor válido e que o código de cobrança está correto.
2. **Determinação do status do pagamento** - Verifica se o pagamento é parcial, completo ou excedente com base nos valores.
3. **Processamento do pagamento** - Envia o pagamento para uma fila (AWS SQS via LocalStack em testes) para processamento posterior.

## Estrutura e Componentes da Aplicação

### 1. `domain` - Regras de Negócio
- **Entities**:
    - `Payment`: Representa o pagamento com atributos como `chargeCode` (código da cobrança), `amount` (valor do pagamento) e `status`.
    - `Seller`: Representa o vendedor, identificado pelo código.
    - `PaymentStatus`: Enum que define os possíveis status do pagamento (PARCIAL, COMPLETO, EXCEDENTE).
    - `PaymentAudit`: Armazena operações de auditoria para monitoramento.
- **Repositories**:
    - `PaymentRepository` e `SellerRepository`: Interfaces para operações de obtenção de dados de pagamento e vendedor.
- **Exceptions**:
    - `SellerNotFoundException`, `PaymentNotFoundException` e `InvalidPaymentStatusException`: Exceções personalizadas para falhas em operações de validação.

### 2. `application` - Lógica de Aplicação
- **Services**:
    - `ValidationService`: Valida a existência de vendedores e códigos de cobrança, usando cache com Redis.
    - `PaymentStatusService`: Usa uma fábrica de estratégias para determinar o status do pagamento.
- **Factory**:
    - `PaymentStatusStrategyFactory`: Seleciona a estratégia apropriada para definir o status do pagamento.
- **Strategies**:
    - Define a lógica para cada status de pagamento (parcial, completo e excedente) utilizando o padrão Strategy.
- **UseCases**:
    - `ValidateAndProcessPaymentUseCase`: Caso de uso principal que valida e processa uma lista de pagamentos.

### 3. `infrastructure` - Integração com Serviços Externos
- **Queues**:
    - `SQSQueueService`: Implementa a interface `QueueService` e usa AWS SQS para enfileirar pagamentos. Resilience4j garante resiliência no envio.
- **Config**:
    - `CacheConfig`: Configuração de cache com Redis.
    - `SecurityConfig` e `JwtAuthenticationFilter`: Configurações de segurança com JWT.
    - `ModelMapperConfig`: Configuração do ModelMapper para conversão entre DTOs e entidades.

### 4. `web` - API REST e Exceções
- **Controllers**:
    - `PaymentController`: Expondo endpoints para processar e listar pagamentos.
- **DTO**:
    - `PaymentRequestDTO`: Representa as solicitações de pagamento.
- **Exception Handling**:
    - `GlobalExceptionHandler`: Controlador global para capturar exceções e retornar respostas apropriadas.

## Camada de Testes

Foram desenvolvidos testes unitários e de integração para garantir a funcionalidade e confiabilidade:
- **Testes Unitários**:
    - Cobrem os serviços, garantindo que cada método funcione conforme esperado.
- **Testes de Integração**:
    - `PaymentIntegrationTest`: Usa `Testcontainers` e `LocalStack` para simular SQS da AWS e verificar o fluxo completo.
    - `PaymentControllerTest`: Testa o controlador e os endpoints REST, validando respostas HTTP.

## Ferramentas e Tecnologias Utilizadas

- **Spring Boot**: Framework principal para construção da aplicação.
- **AWS SQS** (simulado com **LocalStack** durante testes): Para enfileiramento de mensagens.
- **Redis**: Usado como cache para melhorar o desempenho na validação de dados.
- **Resilience4j**: Implementa Circuit Breaker e Retry para resiliência.
- **Testcontainers**: Usado para simular infraestrutura externa.
- **Jacoco**: Para medir a cobertura de testes.
- **Prometheus**: Monitoramento e exposição de métricas via Micrometer.
- **Spring Security**: Para autenticação com JWT.
- **JUnit e Mockito**: Ferramentas de teste para mocks e verificação de comportamento.
- **ModelMapper**: Para conversão entre DTOs e entidades.

## Fluxo de Operações

1. **Processamento de Pagamento**:
    - O cliente faz uma requisição para processar um pagamento (via `PaymentController`).
    - O `ValidateAndProcessPaymentUseCase` valida o vendedor e o código de cobrança.
    - O `PaymentStatusService` determina o status do pagamento.
    - O pagamento é então enfileirado no AWS SQS com o status apropriado.

2. **Monitoramento e Segurança**:
    - Endpoints protegidos com Spring Security e JWT.
    - Resilience4j permite que a aplicação tolere falhas no SQS.
    - Métricas da aplicação estão expostas via Prometheus.

## Configurações Necessárias

### Arquivo `application.properties`

```properties
# Nome do aplicativo
spring.application.name=Payment Validation Project

# Configurações AWS para SQS
aws.sqs.endpoint=https://sqs.sa-east-1.amazonaws.com/<seu-numero-de-conta>/<nome-da-fila>
aws.sqs.region=sa-east-1
aws.sqs.queueName=payment-queue
aws.sqs.accountId=<seu-numero-de-conta>

# Configurações Resilience4j para Circuit Breaker e Retry
resilience4j.circuitbreaker.instances.queueService.slidingWindowSize=10
resilience4j.circuitbreaker.instances.queueService.failureRateThreshold=50
resilience4j.retry.instances.queueService.maxAttempts=3
resilience4j.retry.instances.queueService.waitDuration=1000ms

# Expor endpoints do Actuator para Prometheus
management.endpoints.web.exposure.include=prometheus

# Configurações de Cache com Redis
spring.redis.host=localhost
spring.redis.port=6379
# payment-validation-project
