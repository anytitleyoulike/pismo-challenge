package com.example.pismo.application.controller;

import com.example.pismo.application.dto.response.TransactionResponse;
import com.example.pismo.infrastructure.repository.AccountRepository;
import com.example.pismo.infrastructure.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl() {
        return "http://localhost:" + port + "/transactions";
    }

    private String baseUrlAccounts() {
        return "http://localhost:" + port + "/accounts";
    }

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    private String documentNumber;

    @BeforeEach
    void setUp() {
        documentNumber = "98765432100";
    }

    @AfterEach
    @Transactional
    void tearDown() {
        transactionRepository.deleteAll();
        accountRepository.deleteByDocumentNumber(documentNumber);
    }

    @Test
    void createTransaction_ReturnsOk() {
        var accountRequest = Map.of(
                "document_number", documentNumber
        );
        var newAccount = restTemplate.postForEntity(baseUrlAccounts(), accountRequest, TransactionResponse.class);

        var request = Map.of(
            "account_id", newAccount.getBody().accountId(),
            "operation_type_id", 1,
            "amount", 100.0
        );
        var response = restTemplate.postForEntity(baseUrl(), request, TransactionResponse.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(request.get("account_id"), response.getBody().accountId());
        assertEquals(request.get("operation_type_id"), response.getBody().operationTypeId());
    }

    @Test
    void createTransaction_wrong_operationId() {
        var accountRequest = Map.of(
                "document_number", "12345678900"
        );
        var newAccount = restTemplate.postForEntity(baseUrlAccounts(), accountRequest, TransactionResponse.class);

        var request = Map.of(
                "account_id", newAccount.getBody().accountId(),
                "operation_type_id", 6,
                "amount", 100.0
        );
        var response = restTemplate.postForEntity(baseUrl(), request, TransactionResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void createTransaction_ReturnsNotFoundAccount() {
       var fakeAccount = 9999;

        var request = Map.of(
                "account_id", fakeAccount,
                "operation_type_id", 4,
                "amount", 100.0
        );
        var response = restTemplate.postForEntity(baseUrl(), request, TransactionResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
