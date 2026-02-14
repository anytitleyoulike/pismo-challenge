package com.example.pismo.domain.port;

import com.example.pismo.application.dto.request.TransactionDTO;
import com.example.pismo.domain.entities.TransactionEntity;

public interface TransactionService {

    TransactionEntity createTransaction(TransactionDTO transactionDTO);
}
