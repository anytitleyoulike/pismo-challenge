package com.example.pismo.application.exception;

public class AccountExistentException extends RuntimeException {

    public AccountExistentException(String message) {
        super(message);
    }
}
