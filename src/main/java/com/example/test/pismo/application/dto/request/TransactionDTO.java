package com.example.test.pismo.application.dto.request;

import com.example.test.pismo.domain.enums.OperationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record TransactionDTO(
        @Schema(description = "Identificador único da conta", example = "1"
        )
        @JsonProperty("account_id")
        int accountId,

        @Schema(
                description = "Tipo de operação da transação",
                allowableValues = {"1", "2", "3", "4"}
        )
        @JsonProperty("operation_type_id")
        OperationType operationTypeId,
        @Schema(
                description = "Valor da transação",
                example = "500.00"
        )
        Double amount
        ) {

}
