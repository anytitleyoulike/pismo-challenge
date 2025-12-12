package com.example.test.pismo.application.service.strategy;


import com.example.test.pismo.application.dto.request.TransactionDTO;
import com.example.test.pismo.infrastructure.repository.TransactionRepository;
import com.example.test.pismo.domain.entities.AccountEntity;
import com.example.test.pismo.domain.entities.TransactionEntity;
import com.example.test.pismo.domain.port.Strategy.OperationStrategy;
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
            var negativeTransactions = transactionRepository.findNegativeTransactions(accountEntity.getId());
            var valueLeft = transactionDTO.amount();

            for (TransactionEntity negativeTransaction : negativeTransactions) {
                if (valueLeft > negativeTransaction.getBalance()) {
                    valueLeft += negativeTransaction.getBalance();

            }
            if(valueLeft > 0) {
                negativeTransaction.setBalance(0.0);
            } else {
                negativeTransaction.setBalance(valueLeft);
            }

            transactionRepository.save(negativeTransaction);
        }
            var amount = 0.0;
            if(valueLeft > 0) {
                amount = valueLeft;
            }

            return  transactionRepository.save(new TransactionEntity(
                    accountEntity,
                    Math.abs(transactionDTO.amount()),
                    transactionDTO.operationTypeId().getOperationId(),
                    amount)
            );
        } catch (Exception e) {
            System.out.println("Error processing PAYMENT operation: " + e.getMessage());
            throw e;
        }
    }

    private Double calculateBalance(Double accumulator, Double amount) {
        return amount + accumulator;
    }

}
