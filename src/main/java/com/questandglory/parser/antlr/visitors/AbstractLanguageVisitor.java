package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.GameState;
import com.questandglory.parser.CompilationErrors;
import com.questandglory.parser.LanguageFactory;
import com.questandglory.parser.antlr.ErrorDescription;
import com.questandglory.parser.antlr.LanguageBaseVisitor;
import com.questandglory.parser.antlr.Location;
import lombok.Setter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;

public class AbstractLanguageVisitor<T> extends LanguageBaseVisitor<T> {

    @Setter
    protected GameState gameState;

    @Setter
    protected LanguageFactory factory;

    @Setter
    protected CompilationErrors compilationErrors;

    public AbstractLanguageVisitor() {
    }

    @Override
    public T visitErrorNode(ErrorNode node) {
        Token token = node.getSymbol();
        addError(token.getLine(), token.getCharPositionInLine(), node.getText(), "Unexpected token.");

        return super.visitErrorNode(node);
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

    /**
     * Register a new compilation error for this compilation process.
     *
     * @param ctx     Antlr parser context to extract location.
     * @param message Error message.
     */
    protected void addError(ParserRuleContext ctx, String message) {
        Location location = Location.parse(ctx);
        ErrorDescription error = new ErrorDescription(location, ctx.getText(), message);
        compilationErrors.addError(error);
    }

    /**
     * Register a new compilation error for this compilation process.
     *
     * @param lineNumber Line number (1-based).
     * @param offsetWithinLine Offset within the line (column -- 1-based).
     * @param text The text that caused the error.
     * @param message Error message.
     */
    protected void addError(int lineNumber, int offsetWithinLine, String text, String message) {
        Location location = new Location(lineNumber, offsetWithinLine);
        ErrorDescription error = new ErrorDescription(location, text, message);
        compilationErrors.addError(error);
    }

    protected <T extends ParserRuleContext> T assertExist(T context, ParamsRequestCondition parentContext, String errorMessage) {
        if (context == null) {
            return null;
        }
        return null;
    }

}
