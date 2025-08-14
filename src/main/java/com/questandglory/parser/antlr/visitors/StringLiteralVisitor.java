package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.expressions.string.LiteralStringValueExpression;
import com.questandglory.parser.antlr.LanguageParser;

public class StringLiteralVisitor extends AbstractLanguageVisitor<LiteralStringValueExpression> {

    @Override
    public LiteralStringValueExpression visitStringLiteral(LanguageParser.StringLiteralContext ctx) {
        String text = ctx.getText();
        if (!text.isEmpty() && text.charAt(0) == '"' && text.charAt(text.length() - 1) == '"') {
            // Remove the surrounding quotes
            text = text.substring(1, text.length() - 1);
        }
        return new LiteralStringValueExpression(text);

    }
}
