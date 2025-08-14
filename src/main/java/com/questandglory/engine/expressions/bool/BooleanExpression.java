package com.questandglory.engine.expressions.bool;

import com.questandglory.engine.expressions.AbstractExpression;
import com.questandglory.engine.expressions.Expression;

public abstract class BooleanExpression extends AbstractExpression<Boolean> implements Expression<Boolean> {

    protected BooleanExpression() {
        super(Boolean.class);
    }
}
