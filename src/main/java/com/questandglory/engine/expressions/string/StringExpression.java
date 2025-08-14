package com.questandglory.engine.expressions.string;

import com.questandglory.engine.EngineFacade;
import com.questandglory.engine.expressions.AbstractExpression;
import com.questandglory.engine.expressions.Expression;

public abstract class StringExpression extends AbstractExpression<String> implements Expression<String> {

    protected StringExpression() {
        super(String.class);
    }

    @Override
    public abstract String evaluate(EngineFacade context);
}
