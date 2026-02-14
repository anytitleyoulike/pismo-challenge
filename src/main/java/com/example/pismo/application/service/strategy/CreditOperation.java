package com.example.pismo.application.service.strategy;


import com.example.pismo.application.dto.request.TransactionDTO;
import com.example.pismo.infrastructure.repository.TransactionRepository;
import com.example.pismo.domain.entities.AccountEntity;
import com.example.pismo.domain.entities.TransactionEntity;
import com.example.pismo.domain.port.Strategy.OperationStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("CREDIT")
public class CreditOperation implements OperationStrategy {

    private final TransactionRepository transactionRepository;

    public CreditOperation(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionEntity execute(AccountEntity accountEntity, TransactionDTO transactionDTO) {
        try {
            System.out.println("Processing PAYMENT operation account " + accountEntity.getDocumentNumber() + " for amount: " + transactionDTO.amount());
            var negativeTransactions = transactionRepository.findNegativeTransactions(accountEntity.getId());
            var creditToPay = transactionDTO.amount();

            for (TransactionEntity negativeTransaction : negativeTransactions) {
                if (creditToPay <= 0) {
                    creditToPay = 0.0;
                    break;
                }

                creditToPay += negativeTransaction.getBalance();

                var test = creditToPay >= BigDecimal.ZERO.doubleValue() ? 0.0 : creditToPay;

                negativeTransaction.setBalance(test);

                
                transactionRepository.save(negativeTransaction);
            }
            return  transactionRepository.save(new TransactionEntity(
                    accountEntity,
                    Math.abs(transactionDTO.amount()),
                    transactionDTO.operationTypeId().getOperationId(),
                    creditToPay));

        } catch (Exception e) {
            System.out.println("Error processing PAYMENT operation: " + e.getMessage());
            throw e;
        }
    }

}
