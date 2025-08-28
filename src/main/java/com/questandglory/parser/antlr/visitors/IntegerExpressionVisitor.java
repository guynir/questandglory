package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.constructs.Identifier;
import com.questandglory.engine.expressions.Expression;
import com.questandglory.engine.expressions.integer.ComputedIntegerExpression;
import com.questandglory.engine.expressions.integer.IntegerExpression;
import com.questandglory.engine.expressions.integer.IntegerVariableExpression;
import com.questandglory.engine.expressions.integer.LiteralIntegerValueExpression;
import com.questandglory.parser.CompilationException;
import com.questandglory.parser.antlr.LanguageParser;
import com.questandglory.parser.antlr.Location;

public class IntegerExpressionVisitor extends AbstractLanguageVisitor<IntegerExpression> {

    private Expression<?> expression;

    private String operator;

    @Override
    public IntegerExpression visitStringExpression(LanguageParser.StringExpressionContext ctx) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IntegerExpression visitStringLiteral(LanguageParser.StringLiteralContext ctx) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IntegerExpression visitBooleanLiteral(LanguageParser.BooleanLiteralContext ctx) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IntegerExpression visitIntegerLiteral(LanguageParser.IntegerLiteralContext ctx) {
        return new LiteralIntegerValueExpression(Integer.parseInt(ctx.getText()));
    }

    @Override
    public IntegerExpression visitIdentifier(LanguageParser.IdentifierContext ctx) {
        Identifier variable = factory.identifier(ctx);

        if (variables.isVariableOfType(variable.getName(), Integer.class)) {
            return new IntegerVariableExpression(variable.getName());
        } else {
            Location location = Location.from(ctx);
            throw new CompilationException("Variable '" + variable.getName() + "' must be of Integer type.", location);
        }
    }

    @Override
    public IntegerExpression visitIntegerExpression(LanguageParser.IntegerExpressionContext ctx) {
        if (ctx.left == null) {
            return super.visitIntegerExpression(ctx);
        }

        IntegerExpression left = visit(ctx.left);
        IntegerExpression right = visit(ctx.right);
        String operator = ctx.op.getText();

        return new ComputedIntegerExpression(operator, left, right);
    }

    @Override
    protected IntegerExpression aggregateResult(IntegerExpression aggregate, IntegerExpression nextResult) {
        return aggregate == null ? nextResult : aggregate;
    }
}
