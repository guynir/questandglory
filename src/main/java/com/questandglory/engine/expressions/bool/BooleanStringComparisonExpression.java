package com.questandglory.engine.expressions.bool;

import com.questandglory.engine.EngineFacade;
import com.questandglory.engine.expressions.string.StringExpression;

public class BooleanStringComparisonExpression extends BooleanExpression {

    private final StringExpression left;
    private final StringExpression right;
    private final String operator;

    public BooleanStringComparisonExpression(StringExpression left, StringExpression right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Boolean evaluate(EngineFacade context) {
        return switch (operator.toUpperCase()) {
            case "==" -> left.evaluate(context).equals(right.evaluate(context));
            case "!=" -> !left.evaluate(context).equals(right.evaluate(context));
            default -> throw new IllegalArgumentException("Unknown boolean operator: " + operator);
        };
    }
}
