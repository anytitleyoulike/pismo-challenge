package com.example.pismo.application.service.impl;

import com.example.pismo.application.dto.request.AccountDTO;
import com.example.pismo.application.dto.response.AccountInfoResponse;
import com.example.pismo.application.exception.AccountExistentException;
import com.example.pismo.application.exception.NotFoundException;
import com.example.pismo.infrastructure.repository.AccountRepository;
import com.example.pismo.domain.entities.AccountEntity;
import com.example.pismo.domain.port.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountEntity createAccount(AccountDTO accountDTO) {
        var account = accountRepository.findByDocumentNumber(accountDTO.documentNumber());

        if(account.isPresent()) {
            throw new AccountExistentException("Account with number: " + accountDTO.documentNumber() + " already exists!");
        }

        return accountRepository.save(new AccountEntity(accountDTO.documentNumber()));
    }

    @Override
    public AccountInfoResponse getAccountById(int accountId) {
        AccountEntity accountEntity = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));
        return new AccountInfoResponse(accountEntity.getId(), accountEntity.getDocumentNumber());
    }
}