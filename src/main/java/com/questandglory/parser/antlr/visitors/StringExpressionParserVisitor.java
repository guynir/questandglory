package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.constructs.Identifier;
import com.questandglory.engine.expressions.integer.IntegerExpression;
import com.questandglory.engine.expressions.string.*;
import com.questandglory.parser.antlr.LanguageParser;

public class StringExpressionParserVisitor extends AbstractLanguageVisitor<StringExpression> {

    @Override
    public StringExpression visitIdentifier(LanguageParser.IdentifierContext ctx) {
        Identifier variable = factory.identifier(ctx);

        if (variables.isVariableOfType(variable.getName(), String.class)) {
            return new StringVariableExpression(variable.getName());
        }
        throw new IllegalStateException("Variable '" + variable.getName() + "' must be an String type.");
    }

    @Override
    public StringExpression visitStringExpression(LanguageParser.StringExpressionContext ctx) {
        return super.visitStringExpression(ctx);
    }

    @Override
    public LiteralStringValueExpression visitStringLiteral(LanguageParser.StringLiteralContext ctx) {
        return factory.literalStringExpression(ctx);
    }

    @Override
    public StringExpression visitIntegerExpression(LanguageParser.IntegerExpressionContext ctx) {
        return new ConvertedIntegerStringExpression(factory.integerExpression(ctx));
    }

    @Override
    public StringExpression visitIntegerLiteral(LanguageParser.IntegerLiteralContext ctx) {
        IntegerExpression integerExpression = factory.integerExpression(ctx);
        return new ConvertedIntegerStringExpression(integerExpression);
    }

    @Override
    protected StringExpression aggregateResult(StringExpression aggregate, StringExpression nextResult) {
        if (aggregate == null) {
            aggregate = new StringValueExpression();
        }

        StringValueExpression expression = (StringValueExpression) aggregate;
        if (nextResult != null) {
            expression.addExpression(nextResult);
        }

        return aggregate;
    }
}
