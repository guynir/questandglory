package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.statements.ProgramStatement;
import com.questandglory.parser.antlr.LanguageParser;

public class LanguageVisitor extends AbstractLanguageVisitor<ProgramStatement> {

    @Override
    public ProgramStatement visitProgram(LanguageParser.ProgramContext ctx) {
        return super.visitProgram(ctx);
    }

    @Override
    public ProgramStatement visitStatements(LanguageParser.StatementsContext ctx) {
        return super.visitStatements(ctx);
    }
}
