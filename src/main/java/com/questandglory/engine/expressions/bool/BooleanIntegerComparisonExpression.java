package com.questandglory.engine.expressions.bool;

import com.questandglory.engine.EngineFacade;
import com.questandglory.engine.expressions.integer.IntegerExpression;

public class BooleanIntegerComparisonExpression extends BooleanExpression {

    private final IntegerExpression left;
    private final IntegerExpression right;
    private final String operator;

    public BooleanIntegerComparisonExpression(IntegerExpression left, IntegerExpression right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Boolean evaluate(EngineFacade context) {
        Integer leftValue = left.evaluate(context);
        Integer rightValue = right.evaluate(context);
        int compared = Integer.compare(leftValue, rightValue);

        return switch (operator.toUpperCase()) {
            case "==" -> compared == 0;
            case "!=" -> compared != 0;
            case ">" -> compared > 0;
            case "<" -> compared < 0;
            case ">=" -> compared >= 0;
            case "<=" -> compared <= 0;

            default -> throw new IllegalArgumentException("Unknown integer comparison operator: " + operator);
        };
    }
}
