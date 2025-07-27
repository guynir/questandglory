package com.questandglory.engine.conditions;

import com.questandglory.engine.EngineExecutionContext;

public class ValueExpression<T> implements Expression<T> {

    private final T value;

    public ValueExpression(T value) {
        this.value = value;
    }

    @Override
    public T getValue(EngineExecutionContext context) {
        return value;
    }
}
