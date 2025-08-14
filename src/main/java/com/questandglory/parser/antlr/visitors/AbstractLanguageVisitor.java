package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.GameState;
import com.questandglory.parser.CompilationException;
import com.questandglory.parser.LanguageFactory;
import com.questandglory.parser.antlr.LanguageBaseVisitor;
import com.questandglory.parser.antlr.Location;
import lombok.Setter;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;

public class AbstractLanguageVisitor<T> extends LanguageBaseVisitor<T> {

    @Setter
    protected GameState gameState;

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

    protected boolean isVariableExist(String variableName) {
        return gameState.isDefined(variableName);
    }

    protected boolean isVariableOfType(String variableName, Class<?> variableType) {
        return isVariableExist(variableName) && variableType.isAssignableFrom(gameState.getVariableType(variableName));
    }
}
