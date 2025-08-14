package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.VariableTypeEnum;
import com.questandglory.engine.expressions.Expression;
import com.questandglory.engine.expressions.bool.BooleanExpression;
import com.questandglory.engine.expressions.bool.LiteralBooleanValueExpression;
import com.questandglory.engine.expressions.integer.IntegerExpression;
import com.questandglory.engine.expressions.integer.LiteralIntegerValueExpression;
import com.questandglory.engine.expressions.string.LiteralStringValueExpression;
import com.questandglory.engine.expressions.string.StringExpression;
import com.questandglory.engine.statements.DefineVariableStatement;
import com.questandglory.parser.InternalCompilationErrorException;
import com.questandglory.parser.antlr.LanguageParser;
import com.questandglory.parser.antlr.Location;

public class DefineVariableVisitor extends AbstractLanguageVisitor<DefineVariableStatement> {

    private static final BooleanExpression DEFAULT_BOOLEAN_EXPRESSION = new LiteralBooleanValueExpression(false);
    private static final IntegerExpression DEFAULT_INTEGER_EXPRESSION = new LiteralIntegerValueExpression(0);
    private static final StringExpression DEFAULT_STRING_EXPRESSION = new LiteralStringValueExpression("");

    @Override
    public DefineVariableStatement visitDefineVariableStatement(LanguageParser.DefineVariableStatementContext ctx) {
        String variableName = ctx.variableName.getText();
        VariableTypeEnum variableType = resolveVariableType(ctx.type.getText());
        Expression<?> initialValue;
        if (ctx.initialValue != null) {
            if (ctx.initialValue.integerExpression() != null) {
                initialValue = factory.integerExpression(ctx.initialValue.integerExpression());
            } else if (ctx.initialValue.stringExpression() != null) {
                initialValue = factory.stringExpression(ctx.initialValue.stringExpression());
            } else if (ctx.initialValue.booleanExpression() != null) {
                initialValue = factory.booleanExpression(ctx.initialValue.booleanExpression());
            } else {
                throw new InternalCompilationErrorException("Unsupported initial value expression type.");
            }
        } else {
            initialValue = getDefault(variableType);
        }

        return new DefineVariableStatement(Location.from(ctx), variableName, variableType, initialValue);
    }

    protected VariableTypeEnum resolveVariableType(String variableType) {
        return switch (variableType) {
            case "Integer" -> VariableTypeEnum.INTEGER;
            case "String" -> VariableTypeEnum.STRING;
            case "Boolean" -> VariableTypeEnum.BOOLEAN;
            default -> throw new IllegalStateException("Unexpected value: " + variableType);
        };
    }

    protected Expression<?> getDefault(VariableTypeEnum type) {
        return switch (type) {
            case INTEGER -> DEFAULT_INTEGER_EXPRESSION;
            case STRING -> DEFAULT_STRING_EXPRESSION;
            case BOOLEAN -> DEFAULT_BOOLEAN_EXPRESSION;
        };
    }
}
