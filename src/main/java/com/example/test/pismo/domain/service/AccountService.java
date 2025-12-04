package com.example.test.pismo.domain.service;

import com.example.test.pismo.application.dto.request.AccountDTO;
import com.example.test.pismo.application.dto.response.AccountInfoResponse;
import com.example.test.pismo.application.exception.AccountExistentException;
import com.example.test.pismo.application.exception.NotFoundException;
import com.example.test.pismo.application.repository.AccountRepository;
import com.example.test.pismo.domain.entities.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountEntity createAccount(AccountDTO accountDTO) {
        var account = accountRepository.findByDocumentNumber(accountDTO.documentNumber());

        if(account.isPresent()) {
            throw new AccountExistentException("Account with number: " + accountDTO.documentNumber() + "already exists!");
        }

        return accountRepository.save(new AccountEntity(accountDTO.documentNumber()));
    }

    public AccountInfoResponse getAccountByDocumentNumber(String documentNumber) {
        AccountEntity accountEntity = accountRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new NotFoundException("Account not found"));
        return new AccountInfoResponse(accountEntity.getId(), accountEntity.getDocumentNumber());
    }
}
