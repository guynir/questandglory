package com.questandglory.engine.conditions;

import com.questandglory.engine.EngineExecutionContext;

import java.util.Objects;

public class EqualsCondition implements Condition {

    private final Expression<?> leftExpression;
    private final Expression<?> rightExpression;

    public EqualsCondition(Expression<?> leftExpression, Expression<?> rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    @Override
    public boolean evaluate(EngineExecutionContext context) {
        Object leftValue = leftExpression.getValue(context);
        Object rightValue = rightExpression.getValue(context);

        return Objects.equals(leftValue, rightValue); // Both are null, considered equal
    }
}
