package com.example.test.pismo.domain.service.strategy;

import com.example.test.pismo.application.dto.request.TransactionDTO;
import com.example.test.pismo.domain.entities.AccountEntity;
import com.example.test.pismo.domain.entities.TransactionEntity;

public interface OperationStrategy {

    TransactionEntity execute(AccountEntity accountEntity, TransactionDTO transactionDTO);
}
