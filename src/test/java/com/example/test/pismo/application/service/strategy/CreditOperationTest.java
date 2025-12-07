package com.example.test.pismo.application.service.strategy;

import com.example.test.pismo.application.dto.request.TransactionDTO;
import com.example.test.pismo.domain.entities.AccountEntity;
import com.example.test.pismo.domain.entities.TransactionEntity;
import com.example.test.pismo.infrastructure.repository.TransactionRepository;
import com.example.test.pismo.utils.AccountFactory;
import com.example.test.pismo.utils.TransactionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreditOperationTest {

    private CreditOperation creditOperation;

    @Mock
    private TransactionRepository transactionRepository;

    private AccountEntity accountEntity;
    private TransactionDTO transactionDTO;
    @BeforeEach
    void setUp() {
        creditOperation = new CreditOperation(transactionRepository);
        accountEntity = AccountFactory.create(1, "12345678900");
        transactionDTO = TransactionFactory.createCreditTransactionDTO();
    }


    @Test
    void testExecute_CreditOperation_Success() {
        TransactionEntity transactionEntity = TransactionFactory.createCreditTransactionEntity();

        when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(transactionEntity);

        var result = creditOperation.execute(accountEntity, transactionDTO);

        verify(transactionRepository, times(1)).save(any(TransactionEntity.class));
        assertSame(transactionEntity, result);
        assertEquals(transactionEntity.getAccount(), result.getAccount());
        assertEquals(transactionDTO.amount(), result.getAmount());
    }

    @Test
    void testExecute_CreditOperation_RepositoryThrows() {
        when(transactionRepository.save(any())).thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                creditOperation.execute(accountEntity, transactionDTO)
        );
        assertEquals("DB error", ex.getMessage());

        verify(transactionRepository, times(1)).save(any());
    }


}
