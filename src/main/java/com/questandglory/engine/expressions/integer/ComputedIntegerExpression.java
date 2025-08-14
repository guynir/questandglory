package com.questandglory.engine.expressions.integer;

import com.questandglory.engine.EngineFacade;

public class ComputedIntegerExpression extends IntegerExpression {

    private final String operator; // e.g., "+", "-", "*", "/".
    private final IntegerExpression leftExpression;
    private final IntegerExpression rightExpression;

    public ComputedIntegerExpression() {
        this(null, null, null);
    }

    public ComputedIntegerExpression(String operator,
                                     IntegerExpression leftExpression,
                                     IntegerExpression rightExpression) {
        this.operator = operator;
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    public static ComputedIntegerExpression create(String operator,
                                                   IntegerExpression leftExpression,
                                                   IntegerExpression rightExpression) {
        return new ComputedIntegerExpression(operator, leftExpression, rightExpression);
    }

    public static ComputedIntegerExpression create(String operator,
                                                   int leftExpression,
                                                   IntegerExpression rightExpression) {
        return new ComputedIntegerExpression(operator,
                new LiteralIntegerValueExpression(leftExpression),
                rightExpression);
    }

    public static ComputedIntegerExpression create(String operator,
                                                   IntegerExpression leftExpression,
                                                   int rightExpression) {
        return new ComputedIntegerExpression(operator,
                leftExpression,
                new LiteralIntegerValueExpression(rightExpression));
    }

    public static ComputedIntegerExpression create(String operator,
                                                   int leftExpression,
                                                   int rightExpression) {
        return new ComputedIntegerExpression(operator,
                new LiteralIntegerValueExpression(leftExpression),
                new LiteralIntegerValueExpression(rightExpression));
    }

    @Override
    public Integer evaluate(EngineFacade context) {
        int result;

        switch (operator) {
            case "+" -> result = leftExpression.evaluate(context) + rightExpression.evaluate(context);
            case "-" -> result = leftExpression.evaluate(context) - rightExpression.evaluate(context);

            case "*" -> result = leftExpression.evaluate(context) * rightExpression.evaluate(context);
            case "/" -> {
                Integer divisor = rightExpression.evaluate(context);

                if (divisor != 0) {
                    return leftExpression.evaluate(context) / divisor;
                } else {
                    throw new ArithmeticException("Division by zero");
                }
            }
            default -> throw new UnsupportedOperationException("Operator '" + operator + "' is not supported.");
        }

        return result;
    }
}
