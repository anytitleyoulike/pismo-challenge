package com.example.pismo.application.service.impl;

import com.example.pismo.application.dto.request.AccountDTO;
import com.example.pismo.application.exception.AccountExistentException;
import com.example.pismo.application.exception.NotFoundException;
import com.example.pismo.domain.entities.AccountEntity;
import com.example.pismo.infrastructure.repository.AccountRepository;
import com.example.pismo.utils.AccountFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest {

    private AccountRepository accountRepository;
    private AccountServiceImpl accountServiceImpl;
    private AccountDTO accountDTO;
    private AccountEntity accountEntity;

    @BeforeEach
    public void setup() {
        accountDTO = AccountFactory.createAccountDTO("12345678900");
        accountEntity = AccountFactory.create(1, accountDTO.documentNumber());
        accountRepository = mock(AccountRepository.class);
        accountServiceImpl = new AccountServiceImpl(accountRepository);
    }

    @Test
    public void testCreateAccountWithSuccess() {
        when(accountRepository.findByDocumentNumber(accountDTO.documentNumber())).thenReturn(Optional.empty());
        when(accountRepository.save(any())).thenReturn(accountEntity);

        var result = accountServiceImpl.createAccount(accountDTO);
        assertNotNull(result);
        verify(accountRepository).findByDocumentNumber(accountDTO.documentNumber());
        verify(accountRepository).save(any());
    }

    @Test
    public void testeCreateAccount_alreadyExists_exception() {
        when(accountRepository.findByDocumentNumber(accountDTO.documentNumber())).thenReturn(Optional.of(accountEntity));

        assertThrows(AccountExistentException.class, () -> accountServiceImpl.createAccount(accountDTO));
        verify(accountRepository, times(1)).findByDocumentNumber(accountDTO.documentNumber());
        verify(accountRepository, never()).save(any());
    }

    @Test
    public void testGetAccountById_WithSucess(){
        var id = 1;
        when(accountRepository.findById(id)).thenReturn(Optional.of(accountEntity));
        var result = accountServiceImpl.getAccountById(id);

        verify(accountRepository).findById(id);
        assertNotNull(result);
        assertEquals(result.documentNumber(), accountDTO.documentNumber());
    }

    @Test
    public void testGetAccountById_notFound_exception() {
        int id = 999;
        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountServiceImpl.getAccountById(id));
        verify(accountRepository, times(1)).findById(id);
    }

}
