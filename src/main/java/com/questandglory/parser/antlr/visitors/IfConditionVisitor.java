package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.expressions.bool.BooleanExpression;
import com.questandglory.engine.statements.IfThenStatement;
import com.questandglory.engine.statements.Statement;
import com.questandglory.parser.antlr.LanguageParser;
import com.questandglory.parser.antlr.Location;

public class IfConditionVisitor extends AbstractLanguageVisitor<IfThenStatement> {

    @Override
    public IfThenStatement visitIfConditionStatement(LanguageParser.IfConditionStatementContext ctx) {
        BooleanExpression condition = factory.booleanExpression(ctx.booleanExpression());
        Statement statement = factory.statements(ctx.trueStatements);
        Statement elseStatement = ctx.elseStatements != null ? factory.statements(ctx.elseStatements) : null;
        return new IfThenStatement(Location.from(ctx), condition, statement, elseStatement);
    }
}
