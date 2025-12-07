package com.example.test.pismo.domain.factory;


import com.example.test.pismo.domain.service.strategy.OperationStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OperationFactory {
    private final Map<String, OperationStrategy> strategies;

    public OperationFactory(Map<String, OperationStrategy> strategies) {
        this.strategies = strategies;
    }

    public OperationStrategy getOperation(String type) {
        if(!strategies.containsKey(type)) {
            throw new IllegalArgumentException("Operation type not found: " + type);
        }
        return strategies.get(type);
    }
}
