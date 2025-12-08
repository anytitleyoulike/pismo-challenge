package com.example.test.pismo.application.controller;

import com.example.test.pismo.application.dto.request.AccountDTO;
import com.example.test.pismo.application.dto.response.AccountInfoResponse;
import com.example.test.pismo.domain.port.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountInfoResponse> create(@RequestBody @Valid AccountDTO accountDTO) {
        var newAccount = accountService.createAccount(accountDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new AccountInfoResponse(newAccount.getId(), newAccount.getDocumentNumber())
        );
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountInfoResponse> getAccount(@PathVariable @Valid String accountId) {

        if(accountId == null || accountId.isEmpty() || !accountId.matches("^\\d+$")) {
            throw new IllegalArgumentException("Wrong account ID format");
        }
        var account = accountService.getAccountById(Integer.parseInt(accountId));
        return ResponseEntity.ok(account);
    }
}
