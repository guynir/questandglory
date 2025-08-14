package com.questandglory.engine.expressions.string;

import com.questandglory.engine.EngineFacade;
import com.questandglory.engine.expressions.bool.BooleanExpression;

public class ConvertedBooleanStringExpression extends StringExpression {

    private final BooleanExpression expression;

    public ConvertedBooleanStringExpression(BooleanExpression expression) {
        this.expression = expression;
    }

    @Override
    public String evaluate(EngineFacade context) {
        return String.valueOf(expression.evaluate(context));
    }
}