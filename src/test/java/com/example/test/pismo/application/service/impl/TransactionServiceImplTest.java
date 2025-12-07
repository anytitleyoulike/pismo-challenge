package com.example.test.pismo.application.service.impl;

import com.example.test.pismo.application.dto.request.AccountDTO;
import com.example.test.pismo.application.dto.request.TransactionDTO;
import com.example.test.pismo.application.exception.NotFoundException;
import com.example.test.pismo.domain.entities.AccountEntity;
import com.example.test.pismo.domain.factory.OperationFactory;
import com.example.test.pismo.domain.port.Strategy.OperationStrategy;
import com.example.test.pismo.infrastructure.repository.AccountRepository;
import com.example.test.pismo.utils.AccountFactory;
import com.example.test.pismo.utils.TransactionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private OperationFactory operationFactory;

    @Mock
    private OperationStrategy operationStrategy;

    private TransactionServiceImpl transactionService;
    private AccountDTO dto;
    private AccountEntity account;
    private TransactionDTO transactionDTO;

    @BeforeEach
    void setUp() {
        dto = AccountFactory.createAccountDTO("12345678900");
        transactionDTO = TransactionFactory.createDebitTransactionDTO();
        account = AccountFactory.create(1, dto.documentNumber());
        transactionService = new TransactionServiceImpl(accountRepository, operationFactory);
    }

    @Test
    void createTransaction_accountNotFound_exception() {
        var debitTransaction = TransactionFactory.createDebitTransactionDTO();
        when(accountRepository.findById(debitTransaction.accountId())).thenReturn(Optional.empty());

        var exception = assertThrows(NotFoundException.class, () -> {
            transactionService.createTransaction(debitTransaction);
        });

        verify(accountRepository, times(1)).findById(debitTransaction.accountId());
        verifyNoInteractions(operationFactory);
    }

    @Test
    void createTransaction_purchase_withSucess() {
        var debitTransaction = TransactionFactory.createDebitTransactionDTO();
        when(accountRepository.findById(debitTransaction.accountId())).thenReturn(Optional.of(account));

        var expected = TransactionFactory.createDebitTransactionEntity();
        when(operationFactory.getOperation("DEBIT")).thenReturn(operationStrategy);
        when(operationStrategy.execute(account, debitTransaction)).thenReturn(expected);

        var result = transactionService.createTransaction(debitTransaction);

        assertSame(expected, result);
        assertEquals(expected.getAmount(), result.getAmount());
        verify(operationFactory, times(1)).getOperation("DEBIT");
        verify(operationStrategy, times(1)).execute(account, debitTransaction);
    }

    @Test
    void createTransaction_payment_withSuccess() {
        var creditTransactionDTO = TransactionFactory.createCreditTransactionDTO();

        when(accountRepository.findById(1)).thenReturn(Optional.of(account));

        var expected = TransactionFactory.createCreditTransactionEntity();
        when(operationFactory.getOperation("CREDIT")).thenReturn(operationStrategy);
        when(operationStrategy.execute(account, creditTransactionDTO)).thenReturn(expected);

        var result = transactionService.createTransaction(creditTransactionDTO);

        assertSame(expected, result);
        assertEquals(expected.getAmount(), result.getAmount());
        verify(operationFactory, times(1)).getOperation("CREDIT");
        verify(operationStrategy, times(1)).execute(account, creditTransactionDTO);
    }
}
