package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.statements.Statement;
import com.questandglory.engine.statements.Statements;
import com.questandglory.parser.InternalCompilationErrorException;
import com.questandglory.parser.antlr.LanguageParser;
import com.questandglory.parser.antlr.Location;

public class StatementsVisitor extends AbstractLanguageVisitor<Statements> {

    @Override
    public Statements visitStatements(LanguageParser.StatementsContext ctx) {
        Statements statements = new Statements(Location.from(ctx));
        ctx.statement().forEach(statementContext -> {
            Statement statement = factory.allStatementsDelegator(statementContext);
            if (statement == null) {
                throw new InternalCompilationErrorException("Unknown/unsupported context type: " + statementContext.getClass().getSimpleName());
            }
            statements.add(statement);
        });
        return statements;
    }
}
