package com.example.test.pismo.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class OperationTypeEntity {

    @Id
    @Column(name="operation_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer operationId;

    @Column(name="description")
    private String description;

    public Integer getOperationId() {
        return operationId;
    }
}
