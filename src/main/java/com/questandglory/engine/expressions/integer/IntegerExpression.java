package com.questandglory.engine.expressions.integer;

import com.questandglory.engine.EngineFacade;
import com.questandglory.engine.expressions.AbstractExpression;
import com.questandglory.engine.expressions.Expression;

public abstract class IntegerExpression extends AbstractExpression<Integer> implements Expression<Integer> {

    protected IntegerExpression() {
        super(Integer.class);
    }

    @Override
    public abstract Integer evaluate(EngineFacade context);
}
