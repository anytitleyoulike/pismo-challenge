package com.example.pismo.utils;

import com.example.pismo.application.dto.request.AccountDTO;
import com.example.pismo.domain.entities.AccountEntity;

public class AccountFactory {
    public static AccountDTO createAccountDTO(String documentNumber) {
        return new AccountDTO(documentNumber);
    }

    public static AccountEntity create(Integer id, String documentNumber) {
        return new AccountEntity(id, documentNumber);
    }
}
