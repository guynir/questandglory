package com.questandglory.engine.expressions;

import com.questandglory.engine.statements.Literal;

public interface ExpressionsContainer {

    void addExpression(Expression<?> expression);

    void addLiteral(Literal<?> literal);

}
