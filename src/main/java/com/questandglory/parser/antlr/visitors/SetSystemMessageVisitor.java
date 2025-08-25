package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.expressions.string.StringExpression;
import com.questandglory.engine.statements.SetSystemMessageStatement;
import com.questandglory.parser.antlr.LanguageParser;
import com.questandglory.parser.antlr.Location;

public class SetSystemMessageVisitor extends AbstractLanguageVisitor<SetSystemMessageStatement> {

    @Override
    public SetSystemMessageStatement visitSetChatSystemMessageStatement(
            LanguageParser.SetChatSystemMessageStatementContext ctx) {

        String chatId = ctx.chatId != null ? ctx.chatId.getText() : null;
        StringExpression message = factory.stringExpression(ctx.message);

        return new SetSystemMessageStatement(Location.from(ctx), chatId, message);
    }
}
