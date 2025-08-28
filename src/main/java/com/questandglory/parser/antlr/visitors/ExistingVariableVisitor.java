package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.constructs.Identifier;
import com.questandglory.parser.CompilationException;
import com.questandglory.parser.antlr.LanguageParser;
import com.questandglory.parser.antlr.Location;

public class ExistingVariableVisitor extends AbstractLanguageVisitor<Identifier> {

    @Override
    public Identifier visitVariable(LanguageParser.VariableContext ctx) {
        String variableName = ctx.getText();
        if (!variables.isVariableDefined(variableName)) {
            throw new CompilationException("Variable '" + variableName + "' is not defined.", Location.from(ctx));
        }

        return new Identifier(variableName);
    }
}
