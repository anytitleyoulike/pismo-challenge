package com.example.test.pismo.application.service.impl;

import com.example.test.pismo.application.dto.request.TransactionDTO;
import com.example.test.pismo.application.exception.NotFoundException;
import com.example.test.pismo.infrastructure.repository.AccountRepository;
import com.example.test.pismo.infrastructure.repository.TransactionRepository;
import com.example.test.pismo.domain.entities.TransactionEntity;
import com.example.test.pismo.domain.factory.OperationFactory;
import com.example.test.pismo.domain.port.TransactionService;
import com.example.test.pismo.domain.port.Strategy.OperationStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionSeviceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final OperationFactory operationFactory;


    public TransactionSeviceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, List<OperationStrategy> operationStrategy, OperationFactory operationFactory) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.operationFactory = operationFactory;
    }

    @Override
    public TransactionEntity createTransaction(TransactionDTO transactionDTO) {
            var account = this.accountRepository.findById(transactionDTO.accountId()).
                    orElseThrow(() -> new NotFoundException("Account not found"));

            OperationStrategy operation;
            switch (transactionDTO.operationTypeId()) {
                case PURCHASE, INSTALLMENT_PURCHASE, WITHDRAWAL -> {
                    operation = this.operationFactory.getOperation("DEBIT");
                }
                case PAYMENT -> {
                    operation = this.operationFactory.getOperation("CREDIT");
                }
                default -> throw new IllegalArgumentException("Invalid operation type");
            }

            return operation.execute(account, transactionDTO);
    }
}
