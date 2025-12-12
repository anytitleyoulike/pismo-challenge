package com.example.test.pismo.application.service.strategy;

import com.example.test.pismo.application.dto.request.TransactionDTO;
import com.example.test.pismo.infrastructure.repository.TransactionRepository;
import com.example.test.pismo.domain.entities.AccountEntity;
import com.example.test.pismo.domain.entities.TransactionEntity;
import com.example.test.pismo.domain.port.Strategy.OperationStrategy;
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
            System.out.println("Processing DEBIT operation for amount: " + transactionDTO.amount());

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
