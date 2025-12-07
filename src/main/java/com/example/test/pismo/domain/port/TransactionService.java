package com.example.test.pismo.domain.port;

import com.example.test.pismo.application.dto.request.TransactionDTO;
import com.example.test.pismo.domain.entities.TransactionEntity;

public interface TransactionService {

    TransactionEntity createTransaction(TransactionDTO transactionDTO);
}
