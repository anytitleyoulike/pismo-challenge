package com.example.pismo.application.controller;

import com.example.pismo.application.dto.request.TransactionDTO;
import com.example.pismo.application.dto.response.TransactionResponse;
import com.example.pismo.domain.port.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        var newTransaction = transactionService.createTransaction(transactionDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new TransactionResponse(
                    newTransaction.getId(),
                    newTransaction.getAccount().getId(),
                    newTransaction.getOperationTypeId(),
                    newTransaction.getAmount(),
                    newTransaction.getBalance()
                )
        );

    }
}
