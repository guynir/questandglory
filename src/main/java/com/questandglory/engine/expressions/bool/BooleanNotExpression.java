package com.questandglory.engine.expressions.bool;

import com.questandglory.engine.EngineFacade;

public class BooleanNotExpression extends BooleanExpression {

    private final BooleanExpression expression;

    public BooleanNotExpression(BooleanExpression expression) {
        this.expression = expression;
    }

    @Override
    public Boolean evaluate(EngineFacade context) {
        return !expression.evaluate(context);
    }
}
