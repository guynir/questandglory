package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.constructs.Identifier;
import com.questandglory.engine.expressions.bool.*;
import com.questandglory.parser.antlr.LanguageParser;

public class BooleanExpressionParserVisitor extends AbstractLanguageVisitor<BooleanExpression> {

    @Override
    public BooleanExpression visitIdentifier(LanguageParser.IdentifierContext ctx) {
        Identifier variable = factory.identifier(ctx);

        if (isVariableOfType(variable.getName(), Boolean.class)) {
            return new BooleanVariableExpression(variable.getName());
        } else {
            addError(ctx, "Variable '" + variable.getName() + "' must be of Boolean type.");
            return null;
        }
    }

    @Override
    public BooleanExpression visitBooleanExpression(LanguageParser.BooleanExpressionContext ctx) {
        if (ctx.left == null && ctx.right == null) {
            return super.visitBooleanExpression(ctx);
        }

        BooleanExpression left = visit(ctx.left);
        BooleanExpression right = visit(ctx.right);
        String operator = ctx.op.getText();
        return new BooleanBinaryExpression(left, right, operator);
    }

    @Override
    public BooleanExpression visitBooleanUnaryExpression(LanguageParser.BooleanUnaryExpressionContext ctx) {
        return new BooleanNotExpression(ctx.booleanExpression().accept(this));
    }

    @Override
    public BooleanExpression visitBooleanLiteral(LanguageParser.BooleanLiteralContext ctx) {
        return new LiteralBooleanValueExpression(Boolean.parseBoolean(ctx.getText()));
    }

    @Override
    public BooleanExpression visitStringBinaryExpression(LanguageParser.StringBinaryExpressionContext ctx) {
        return new BooleanStringComparisonExpression(
                factory.stringExpression(ctx.left),
                factory.stringExpression(ctx.right),
                ctx.op.getText());
    }

    @Override
    public BooleanExpression visitIntegerBinaryExpression(LanguageParser.IntegerBinaryExpressionContext ctx) {
        return new BooleanIntegerComparisonExpression(
                factory.integerExpression(ctx.left),
                factory.integerExpression(ctx.right),
                ctx.op.getText());
    }
}
