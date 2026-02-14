package com.example.pismo.application.service.strategy;

import com.example.pismo.application.dto.request.TransactionDTO;
import com.example.pismo.infrastructure.repository.TransactionRepository;
import com.example.pismo.domain.entities.AccountEntity;
import com.example.pismo.domain.entities.TransactionEntity;
import com.example.pismo.domain.port.Strategy.OperationStrategy;
import org.springframework.stereotype.Component;

@Component("DEBIT")
public class DebitOperation implements OperationStrategy {
    private final TransactionRepository transactionRepository;

    public DebitOperation(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionEntity execute(AccountEntity accountEntity,TransactionDTO transactionDTO) {
        try {
            System.out.println("Processing DEBIT operation for amount: " + -Math.abs(transactionDTO.amount()));

            return transactionRepository.save(
                    new TransactionEntity(
                            accountEntity,
                            -Math.abs(transactionDTO.amount()),
                            transactionDTO.operationTypeId().getOperationId(),
                            -Math.abs(transactionDTO.amount()))

            );
        } catch(Exception e) {
            System.out.println("Error processing DEBIT operation: "+ e.getMessage());
            throw e;
        }
    }
}
