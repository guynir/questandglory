package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.constructs.Identifier;
import com.questandglory.engine.statements.InputStatement;
import com.questandglory.parser.antlr.LanguageParser;
import com.questandglory.parser.antlr.Location;

public class InputStatementVisitor extends AbstractLanguageVisitor<InputStatement> {

    @Override
    public InputStatement visitInputStatement(LanguageParser.InputStatementContext ctx) {
        Identifier identifier = factory.identifier(ctx.identifier());
        return new InputStatement(Location.from(ctx), identifier.getName());
    }
}
