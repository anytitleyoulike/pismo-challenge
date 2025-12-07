package com.example.test.pismo.utils;

import com.example.test.pismo.application.dto.request.TransactionDTO;
import com.example.test.pismo.domain.entities.AccountEntity;
import com.example.test.pismo.domain.entities.TransactionEntity;
import com.example.test.pismo.domain.enums.OperationType;
import com.example.test.pismo.domain.factory.OperationFactory;

public class TransactionFactory {

    //create methods to create TransactionDTO and TransactionEntity objects for testing purposes

    public static TransactionDTO createDebitTransactionDTO(){
        return new TransactionDTO(1, OperationType.PURCHASE, 100.0);
    }

    public static TransactionDTO createCreditTransactionDTO(){
        return new TransactionDTO(1, OperationType.PAYMENT, 100.0);
    }

    public static TransactionEntity createDebitTransactionEntity(){
        return new TransactionEntity(new AccountEntity(1,"11111111111"),
                -100.0,
                OperationType.PURCHASE.getOperationId());
    }

    public static TransactionEntity createCreditTransactionEntity(){
        return new TransactionEntity(new AccountEntity(1,"11111111111"),
                100.0,
                OperationType.PAYMENT.getOperationId());
    }
}
