package com.questandglory.parser.antlr.visitors;

import com.questandglory.language.variables.VariablesDefinition;
import com.questandglory.parser.CompilationException;
import com.questandglory.parser.LanguageFactory;
import com.questandglory.parser.antlr.LanguageBaseVisitor;
import com.questandglory.parser.antlr.Location;
import lombok.Setter;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;

public class AbstractLanguageVisitor<T> extends LanguageBaseVisitor<T> {

    @Setter
    protected VariablesDefinition variables;

    @Setter
    protected LanguageFactory factory;

    public AbstractLanguageVisitor() {
    }

    @Override
    public T visitErrorNode(ErrorNode node) {
        Token token = node.getSymbol();
        Location location = Location.from(token);
        throw new CompilationException("Unexpected token.", location);
    }

    @Override
    protected T aggregateResult(T aggregate, T nextResult) {
        return aggregate != null ? aggregate : nextResult;
    }
}
