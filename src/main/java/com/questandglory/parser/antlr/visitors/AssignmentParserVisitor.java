package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.constructs.Identifier;
import com.questandglory.engine.expressions.Expression;
import com.questandglory.engine.expressions.bool.BooleanExpression;
import com.questandglory.engine.expressions.integer.IntegerExpression;
import com.questandglory.engine.expressions.string.StringExpression;
import com.questandglory.engine.statements.AssignmentStatement;
import com.questandglory.parser.InternalCompilationErrorException;
import com.questandglory.parser.antlr.LanguageParser;
import com.questandglory.parser.antlr.Location;

public class AssignmentParserVisitor extends AbstractLanguageVisitor<Object> {

    @Override
    public Identifier visitIdentifier(LanguageParser.IdentifierContext ctx) {
        return new Identifier(ctx.getText());
    }

    @Override
    public AssignmentStatement visitAssignmentStatement(LanguageParser.AssignmentStatementContext ctx) {
        Identifier identifier = (Identifier) super.visit(ctx.identifier());
        Expression<?> expression;
        if (ctx.integerExpression() != null) {
            expression = (IntegerExpression) super.visit(ctx.integerExpression());
        } else if (ctx.stringExpression() != null) {
            expression = (StringExpression) super.visit(ctx.stringExpression());
        } else if (ctx.booleanExpression() != null) {
            expression = (BooleanExpression) super.visit(ctx.booleanExpression());
        } else {
            throw new InternalCompilationErrorException("Unsupported expression.");
        }

        Class<?> variableType = gameState.getVariableType(identifier.getName());
        if (!expression.getType().equals(variableType)) {
            throw new IllegalArgumentException("Variable '"
                    + identifier.getName()
                    + "' of type "
                    + variableType.getSimpleName()
                    + " cannot be assigned an expression of type "
                    + expression.getType().getSimpleName());
        }
        return new AssignmentStatement(Location.from(ctx), identifier.getName(), expression);
    }

    @Override
    public IntegerExpression visitIntegerExpression(LanguageParser.IntegerExpressionContext ctx) {
        return factory.integerExpression(ctx);
    }

    @Override
    public StringExpression visitStringExpression(LanguageParser.StringExpressionContext ctx) {
        return factory.stringExpression(ctx);
    }

    @Override
    public Object visitBooleanExpression(LanguageParser.BooleanExpressionContext ctx) {
        return factory.booleanExpression(ctx);
    }
}
