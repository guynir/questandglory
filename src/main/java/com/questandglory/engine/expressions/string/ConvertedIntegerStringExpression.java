package com.questandglory.engine.expressions.string;

import com.questandglory.engine.EngineFacade;
import com.questandglory.engine.expressions.integer.IntegerExpression;

public class ConvertedIntegerStringExpression extends StringExpression {

    private final IntegerExpression expression;

    public ConvertedIntegerStringExpression(IntegerExpression expression) {
        this.expression = expression;
    }

    @Override
    public String evaluate(EngineFacade context) {
        return String.valueOf(expression.evaluate(context));
    }
}