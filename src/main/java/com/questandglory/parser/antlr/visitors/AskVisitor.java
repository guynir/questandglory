package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.constructs.Identifier;
import com.questandglory.engine.expressions.string.StringExpression;
import com.questandglory.engine.statements.AskStatement;
import com.questandglory.parser.antlr.LanguageParser;
import com.questandglory.parser.antlr.Location;

public class AskVisitor extends AbstractLanguageVisitor<AskStatement> {
    @Override
    public AskStatement visitAskStatement(LanguageParser.AskStatementContext ctx) {
        Identifier target = factory.identifier(ctx.target);
        String chatId = ctx.chatId != null ? ctx.chatId.getText() : null;
        StringExpression message = factory.stringExpression(ctx.message);

        return new AskStatement(Location.from(ctx), target, chatId, message);
    }
}
