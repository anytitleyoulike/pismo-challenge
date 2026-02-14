package com.example.pismo.utils;

import com.example.pismo.application.dto.request.TransactionDTO;
import com.example.pismo.domain.entities.AccountEntity;
import com.example.pismo.domain.entities.TransactionEntity;
import com.example.pismo.domain.enums.OperationType;

import java.util.ArrayList;
import java.util.List;

public class TransactionFactory {

    public static TransactionDTO createDebitTransactionDTO() {
        return new TransactionDTO(1, OperationType.PURCHASE, 100.0);
    }

    public static TransactionDTO createCreditTransactionDTO(){
        return new TransactionDTO(1, OperationType.PAYMENT, 100.0);
    }

    public static TransactionEntity createDebitTransactionEntity(){
        return new TransactionEntity(new AccountEntity(1,"11111111111"),
                -100.0,
                OperationType.PURCHASE.getOperationId(), -100.0);
    }

    public static TransactionEntity createCreditTransactionEntity(){
        return new TransactionEntity(new AccountEntity(1,"11111111111"),
                100.0,
                OperationType.PAYMENT.getOperationId(), 100.0);
    }

    public static List<TransactionEntity> createListOfTransactions(){
        List<TransactionEntity> transactions = new ArrayList<>();
        var account = new AccountEntity(1,"11111111111");
        transactions.add(new TransactionEntity(account, -50.0, 1, 0.0));
        transactions.add(new TransactionEntity(account, -23.5, 1, -13.5));
        transactions.add(new TransactionEntity(account, -18.7, 1, -18.7));

        return transactions;
    }
}
