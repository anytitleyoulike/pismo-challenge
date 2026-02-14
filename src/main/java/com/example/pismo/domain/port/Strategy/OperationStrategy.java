package com.example.pismo.domain.port.Strategy;

import com.example.pismo.application.dto.request.TransactionDTO;
import com.example.pismo.domain.entities.AccountEntity;
import com.example.pismo.domain.entities.TransactionEntity;

public interface OperationStrategy {

    TransactionEntity execute(AccountEntity accountEntity, TransactionDTO transactionDTO);
}
