package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.constructs.Identifier;
import com.questandglory.parser.antlr.LanguageParser;

public class IdentifierVisitor extends AbstractLanguageVisitor<Identifier> {

    @Override
    public Identifier visitIdentifier(LanguageParser.IdentifierContext ctx) {
        return new Identifier(ctx.getText());
    }

}
