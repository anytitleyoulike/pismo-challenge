package com.example.pismo.application.exception.dto;

public class ErrorResponse {
    private int statusCode;
    private String details;

    public ErrorResponse(int statusCode, String details) {
        this.statusCode = statusCode;
        this.details = details;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getDetails() {
        return details;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
