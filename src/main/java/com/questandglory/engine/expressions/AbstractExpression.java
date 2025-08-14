package com.questandglory.engine.expressions;

import com.questandglory.engine.EngineFacade;

public abstract class AbstractExpression<T> implements Expression<T> {

    private final Class<T> type;

    protected AbstractExpression(Class<T> type) {
        this.type = type;
    }

    @Override
    public abstract T evaluate(EngineFacade facade);

    @Override
    public Class<T> getType() {
        return type;
    }

}
