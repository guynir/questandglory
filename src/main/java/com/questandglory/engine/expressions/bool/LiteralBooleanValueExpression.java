package com.questandglory.engine.expressions.bool;

import com.questandglory.engine.EngineFacade;

public class LiteralBooleanValueExpression extends BooleanExpression {

    private final boolean value;

    public LiteralBooleanValueExpression(boolean value) {
        this.value = value;
    }

    @Override
    public Boolean evaluate(EngineFacade context) {
        return value;
    }
}
