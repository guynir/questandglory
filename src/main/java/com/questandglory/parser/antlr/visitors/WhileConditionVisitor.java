package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.expressions.bool.BooleanExpression;
import com.questandglory.engine.statements.Statement;
import com.questandglory.engine.statements.WhileConditionStatement;
import com.questandglory.parser.antlr.LanguageParser;
import com.questandglory.parser.antlr.Location;

public class WhileConditionVisitor extends AbstractLanguageVisitor<WhileConditionStatement> {

    @Override
    public WhileConditionStatement visitWhileConditionStatement(LanguageParser.WhileConditionStatementContext ctx) {
        BooleanExpression condition = factory.booleanExpression(ctx.booleanExpression());
        Statement statement = factory.statements(ctx.statements());

        return new WhileConditionStatement(Location.from(ctx), condition, statement);
    }
}
