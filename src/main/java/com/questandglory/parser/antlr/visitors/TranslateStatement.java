package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.EngineFacade;
import com.questandglory.engine.constructs.Identifier;
import com.questandglory.engine.expressions.string.StringExpression;
import com.questandglory.engine.statements.Statement;
import com.questandglory.parser.antlr.Location;

import java.util.Locale;

public class TranslateStatement extends Statement {

    private final Identifier targetVariable;

    private final Locale language;

    private final StringExpression text;

    public TranslateStatement(Location location, Identifier targetVariable, Locale language, StringExpression text) {
        super(location);
        this.targetVariable = targetVariable;
        this.language = language;
        this.text = text;
    }

    @Override
    protected void handleInternal(EngineFacade facade) throws InterruptedException {
        String translated = facade.translate(facade.render(text.evaluate(facade)), language);
        facade.state().setVariable(targetVariable, translated);
    }
}
