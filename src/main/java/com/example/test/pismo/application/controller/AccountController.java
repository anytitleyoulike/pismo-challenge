package com.example.test.pismo.application.controller;

import com.example.test.pismo.application.dto.request.AccountDTO;
import com.example.test.pismo.application.dto.response.AccountInfoResponse;
import com.example.test.pismo.domain.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountInfoResponse> create(@RequestBody @Validated AccountDTO accountDTO) {
        var newAccount = accountService.createAccount(accountDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new AccountInfoResponse(newAccount.getId(), newAccount.getDocumentNumber())
        );
    }

    @GetMapping("/{documentNumber}")
    public AccountInfoResponse getAccount(@PathVariable String documentNumber) {
        if(documentNumber == null || documentNumber.isEmpty()) {
            throw new IllegalArgumentException("Document number must not be null or empty");
        }

        var account = accountService.getAccountByDocumentNumber(documentNumber);
        return ResponseEntity.ok(account).getBody();
    }
}
