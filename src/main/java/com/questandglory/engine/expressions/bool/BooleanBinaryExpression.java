package com.questandglory.engine.expressions.bool;

import com.questandglory.engine.EngineFacade;

public class BooleanBinaryExpression extends BooleanExpression {

    private final BooleanExpression left;
    private final BooleanExpression right;
    private final String operator;

    public BooleanBinaryExpression(BooleanExpression left, BooleanExpression right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Boolean evaluate(EngineFacade context) {
        return switch (operator.toUpperCase()) {
            case "AND" -> left.evaluate(context) && right.evaluate(context);
            case "OR" -> left.evaluate(context) || right.evaluate(context);
            case "==" -> left.evaluate(context).equals(right.evaluate(context));
            case "!=" -> !left.evaluate(context).equals(right.evaluate(context));
            default -> throw new IllegalArgumentException("Unknown boolean operator: " + operator);
        };
    }
}
