package com.questandglory.engine.conditions;

import com.questandglory.engine.EngineFacade;
import com.questandglory.engine.expressions.Expression;

import java.util.Objects;

public class EqualsCondition implements Condition {

    private final Expression<?> leftExpression;
    private final Expression<?> rightExpression;

    public EqualsCondition(Expression<?> leftExpression, Expression<?> rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    @Override
    public boolean evaluate(EngineFacade context) {
        Object leftValue = leftExpression.evaluate(context);
        Object rightValue = rightExpression.evaluate(context);

        return Objects.equals(leftValue, rightValue); // Both are null, considered equal
    }
}
