package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.statements.ProgramStatement;
import com.questandglory.engine.statements.Statements;
import com.questandglory.parser.antlr.LanguageParser;
import com.questandglory.parser.antlr.Location;

public class ProgramVisitor extends AbstractLanguageVisitor<ProgramStatement> {

    @Override
    public ProgramStatement visitProgram(LanguageParser.ProgramContext ctx) {
        Statements statements = factory.statements(ctx.statements());
        ProgramStatement program = new ProgramStatement(Location.from(ctx));
        program.setStatements(statements);

        return program;
    }

}
