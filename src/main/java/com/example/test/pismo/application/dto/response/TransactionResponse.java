package com.example.test.pismo.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TransactionResponse(
        @JsonProperty("transaction_id")
        int transactionId,
        @JsonProperty("account_id")
        int accountId,
        @JsonProperty("operation_type_id")
        int operationTypeId,
        Double amount

) {
}
