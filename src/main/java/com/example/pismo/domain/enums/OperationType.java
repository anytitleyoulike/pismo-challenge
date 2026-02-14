package com.example.pismo.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OperationType {
    PURCHASE(1),
    INSTALLMENT_PURCHASE(2),
    WITHDRAWAL(3),
    PAYMENT(4);

    private final int operationId;

    OperationType(int id) {
        this.operationId = id;
    }

    public int getOperationId() {
        return operationId;
    }

    @JsonCreator
    public static OperationType validOperationType(int id) {
       for(OperationType type : OperationType.values()) {
           if(type.getOperationId() == id) {
               return type;
           }
       }

        throw new IllegalArgumentException("Invalid operation: "+id);
    }
}
