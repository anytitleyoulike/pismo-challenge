package com.example.pismo.domain.port;

import com.example.pismo.application.dto.request.AccountDTO;
import com.example.pismo.application.dto.response.AccountInfoResponse;
import com.example.pismo.domain.entities.AccountEntity;

public interface AccountService {
    AccountEntity createAccount(AccountDTO accountDTO);
    AccountInfoResponse getAccountById(int accountId);
}
