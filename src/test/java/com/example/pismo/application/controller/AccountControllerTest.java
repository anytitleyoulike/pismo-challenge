package com.example.pismo.application.controller;

import com.example.pismo.application.dto.response.AccountInfoResponse;
import com.example.pismo.infrastructure.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl() {
        return "http://localhost:" + port + "/accounts";
    }
    private String documentNumber;

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        documentNumber = "98765432100";
    }

    @AfterEach
    @Transactional
    void tearDown() {
        accountRepository.deleteByDocumentNumber(documentNumber);
    }

    @Test
    void createAccount_ReturnsCreated() {
        var request = Map.of("document_number", documentNumber);
        ResponseEntity<AccountInfoResponse> response = restTemplate.postForEntity(baseUrl(), request, AccountInfoResponse.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().accountId());
        assertEquals(documentNumber, response.getBody().documentNumber());
    }

    @Test
    void getAccount_ReturnsOk() {
        var request = Map.of("document_number", documentNumber);
        var response = restTemplate.postForEntity(baseUrl(), request, AccountInfoResponse.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Integer createdId = response.getBody().accountId();

        var getResp = restTemplate.getForEntity(baseUrl() + "/" + createdId, AccountInfoResponse.class);
        assertEquals(HttpStatus.OK, getResp.getStatusCode());
        assertNotNull(getResp.getBody());
        assertEquals(createdId, getResp.getBody().accountId());
        assertEquals(documentNumber, getResp.getBody().documentNumber());
    }

}
