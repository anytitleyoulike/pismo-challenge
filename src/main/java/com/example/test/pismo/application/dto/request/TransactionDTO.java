package com.example.test.pismo.application.dto.request;

import com.example.test.pismo.domain.enums.OperationType;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TransactionDTO(

        @JsonProperty("account_id")
        int accountId,
        @JsonProperty("operation_type_id")
        OperationType operationTypeId,
        Double amount
        ) {

}
