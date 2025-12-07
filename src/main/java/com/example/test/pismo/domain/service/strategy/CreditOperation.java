package com.example.test.pismo.domain.service.strategy;


import com.example.test.pismo.application.dto.request.TransactionDTO;
import com.example.test.pismo.application.repository.TransactionRepository;
import com.example.test.pismo.domain.entities.AccountEntity;
import com.example.test.pismo.domain.entities.TransactionEntity;
import org.springframework.stereotype.Component;

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
            return  transactionRepository.save(new TransactionEntity(
                    accountEntity,
                    Math.abs(transactionDTO.amount()),
                    transactionDTO.operationTypeId().getOperationId())
            );
        } catch (Exception e) {
            System.out.println("Error processing PAYMENT operation: " + e.getMessage());
            throw e;
        }
    }

}
