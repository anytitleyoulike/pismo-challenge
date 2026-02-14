package com.example.pismo.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountInfoResponse(
        @JsonProperty("account_id")
        Integer accountId,
        @JsonProperty("document_number")
        String documentNumber
){}
