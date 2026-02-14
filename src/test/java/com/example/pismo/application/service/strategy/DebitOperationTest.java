package com.example.pismo.application.service.strategy;

import com.example.pismo.application.dto.request.TransactionDTO;
import com.example.pismo.domain.entities.AccountEntity;
import com.example.pismo.domain.entities.TransactionEntity;
import com.example.pismo.infrastructure.repository.TransactionRepository;
import com.example.pismo.utils.AccountFactory;
import com.example.pismo.utils.TransactionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class DebitOperationTest {

    private DebitOperation debitOperation;


    @Mock
    private TransactionRepository transactionRepository;

    private AccountEntity accountEntity;
    private TransactionDTO transactionDTO;
    @BeforeEach
    void setUp() {
        debitOperation = new DebitOperation(transactionRepository);
        accountEntity = AccountFactory.create(1, "12345678900");
        transactionDTO = TransactionFactory.createDebitTransactionDTO();
    }


    @Test
    void testExecute_DebitOperation_Success() {
        TransactionEntity transactionEntity = TransactionFactory.createDebitTransactionEntity();

        when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(transactionEntity);

        var result = debitOperation.execute(accountEntity, transactionDTO);

        verify(transactionRepository, times(1)).save(any(TransactionEntity.class));
        assertSame(transactionEntity, result);
        assertEquals(transactionEntity.getAccount(), result.getAccount());
        assertEquals(-transactionDTO.amount(), result.getAmount());
    }

    @Test
    void testExecute_CreditOperation_RepositoryThrows() {
        when(transactionRepository.save(any())).thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                debitOperation.execute(accountEntity, transactionDTO)
        );
        verify(transactionRepository, times(1)).save(any());
    }


}