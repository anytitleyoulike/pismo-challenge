package com.example.test.pismo.application.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;

public record AccountDTO(
        @Schema(description = "Número do documento da conta", example = "12345678901")
        @Max(11)
        @JsonProperty("document_number")
        String documentNumber) {

}
