package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.expressions.string.StringExpression;
import com.questandglory.engine.statements.MessageStatement;
import com.questandglory.parser.antlr.LanguageParser;
import com.questandglory.parser.antlr.Location;

public class MessageStatementVisitor extends AbstractLanguageVisitor<MessageStatement> {

    @Override
    public MessageStatement visitMessageStatement(LanguageParser.MessageStatementContext ctx) {
        StringExpression expression = factory.stringExpression(ctx.message);
        return new MessageStatement(Location.from(ctx), expression);
    }
}
