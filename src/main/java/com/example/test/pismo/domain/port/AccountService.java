package com.example.test.pismo.domain.port;

import com.example.test.pismo.application.dto.request.AccountDTO;
import com.example.test.pismo.application.dto.response.AccountInfoResponse;
import com.example.test.pismo.domain.entities.AccountEntity;

public interface AccountService {
    AccountEntity createAccount(AccountDTO accountDTO);
    AccountInfoResponse getAccountById(int accountId);
}
