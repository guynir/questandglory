package com.questandglory.engine.expressions.string;

import com.questandglory.engine.EngineFacade;
import com.questandglory.engine.expressions.Expression;
import com.questandglory.engine.expressions.ExpressionsContainer;
import com.questandglory.engine.statements.Literal;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class StringValueExpression extends StringExpression implements ExpressionsContainer {

    private final List<Expression<?>> values = new LinkedList<>();

    public StringValueExpression(String... values) {
        for (String value : values) {
            addExpression(value);
        }
    }

    public void addExpression(String value) {
        values.add(new LiteralStringValueExpression(value));
    }

    public void addExpression(Expression<?> expression) {
        values.add(expression);
    }

    @Override
    public void addLiteral(Literal<?> literal) {
        addExpression(literal.toString());
    }

    @Override
    public String evaluate(EngineFacade context) {
        final Set<Class<?>> supportedTypes = Set.of(
                String.class, Integer.class, Long.class, Boolean.class, Float.class, Double.class);

        StringBuilder result = new StringBuilder();
        for (Expression<?> value : values) {
            Object expressionResult = value.evaluate(context);
            if (supportedTypes.contains(expressionResult.getClass())) {
                result.append(expressionResult);
            } else {
                throw new IllegalArgumentException("Unsupported type: " + expressionResult.getClass());
            }
        }

        return result.toString();
    }
}
