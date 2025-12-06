package com.example.test.pismo.domain.service.impl;

import com.example.test.pismo.application.dto.request.TransactionDTO;
import com.example.test.pismo.application.exception.NotFoundException;
import com.example.test.pismo.application.repository.AccountRepository;
import com.example.test.pismo.application.repository.TransactionRepository;
import com.example.test.pismo.domain.entities.TransactionEntity;
import com.example.test.pismo.domain.service.TransactionService;
import org.springframework.stereotype.Component;

@Component
public class TransactionSeviceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionSeviceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public TransactionEntity createTransaction(TransactionDTO transactionDTO) {
            var account = this.accountRepository.findById(transactionDTO.accountId()).
                    orElseThrow(() -> new NotFoundException("Account not found"));

            var adjustedAmount = 0.0;
            switch (transactionDTO.operationTypeId()) {
                case PURCHASE, INSTALLMENT_PURCHASE, WITHDRAWAL -> {
                    adjustedAmount = -Math.abs(transactionDTO.amount());
                }
                case PAYMENT -> {
                   adjustedAmount = Math.abs(transactionDTO.amount());
                }
                default -> throw new IllegalArgumentException("Invalid operation type");
            }

            return transactionRepository.save(new TransactionEntity(
                    account,
                    adjustedAmount,
                    transactionDTO.operationTypeId().getOperationId())
            );
    }
}
