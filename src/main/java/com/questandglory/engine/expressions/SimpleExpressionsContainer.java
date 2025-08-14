package com.questandglory.engine.expressions;

import com.questandglory.engine.statements.Literal;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class SimpleExpressionsContainer implements ExpressionsContainer {

    @Getter
    private final List<Expression<?>> expressions = new ArrayList<>();

    public void addExpression(Expression<?> expression) {
        expressions.add(expression);
    }

    @Override
    public void addLiteral(Literal<?> literal) {
        throw new UnsupportedOperationException();
    }
}
