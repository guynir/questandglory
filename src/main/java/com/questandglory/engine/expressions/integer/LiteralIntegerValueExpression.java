package com.questandglory.engine.expressions.integer;

import com.questandglory.engine.EngineFacade;

public class LiteralIntegerValueExpression extends IntegerExpression {

    private final int value;

    public LiteralIntegerValueExpression(int value) {
        this.value = value;
    }

    @Override
    public Integer evaluate(EngineFacade context) {
        return value;
    }
}
