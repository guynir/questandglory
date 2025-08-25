package com.questandglory.engine;

import com.questandglory.engine.constructs.Identifier;
import com.questandglory.parser.CompilationException;
import com.questandglory.parser.antlr.Location;
import org.antlr.v4.runtime.ParserRuleContext;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * An in-memory implementation of the GameState.
 *
 * @author Guy Raz Nir
 * @since 2025/07/31
 */
public class InMemoryGameState implements GameState {

    /**
     * Holds the game state.
     */
    private final Map<String, Object> variables = new HashMap<>();

    private final Map<String, Class<?>> variableTypes = new HashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getVariable(String name) throws IllegalArgumentException, UnknownVariableException, ClassCastException {
        Assert.hasText(name, "Variable name is null or empty.");
        Object value = variables.get(name);
        if (value == null) {
            throw new UnknownVariableException("Undefined variable:  '" + name + "'.");
        }
        return (T) value;
    }

    @Override
    public void setVariable(String name, Object value) {
        Assert.hasText(name, "Variable name is null or empty.");
        Class<?> type = variableTypes.get(name);
        if (type == null) {
            throw new IllegalArgumentException("Variable '" + name + "' is not defined.");
        }
        if (!type.isInstance(value)) {
            throw new IllegalStateException("Variable '"
                    + name
                    + "' type mismatch: expected "
                    + type.getName()
                    + ", but got "
                    + (value != null ? value.getClass().getName() : "null")
                    + ".");
        }
        variables.put(name, value);
    }

    @Override
    public void setVariable(Identifier identifier, Object value) {
        Assert.notNull(identifier, "Identifier cannot be null.");
        Assert.hasText(identifier.getName(), "Identifier name is null or empty.");
        setVariable(identifier.getName(), value);
    }

    @Override
    public void removeVariable(String name) {
        variables.remove(name);
    }

    @Override
    public Map<String, Object> getVariables() {
        return variables;
    }

    @Override
    public <T> void registerVariable(String name, Class<T> type) throws IllegalArgumentException, IllegalStateException {
        registerVariable(name, type, null);
    }

    @Override
    public <T> void registerVariable(String name, Class<T> type, T value) throws IllegalArgumentException {
        Assert.hasText(name, "Variable name is null or empty.");
        Assert.notNull(type, "Variable type cannot be null.");
        variableTypes.put(name, type);
        variables.put(name, value);
    }

    public boolean isVariableDefined(String name) {
        Assert.hasText(name, "Variable name is null or empty.");
        return variableTypes.containsKey(name);
    }

    public Class<?> getVariableType(String name) {
        Assert.hasText(name, "Variable name is null or empty.");
        Class<?> type = variableTypes.get(name);
        if (type == null) {
            throw new UnknownVariableException("Undefined variable: '" + name + "'.");
        }
        return type;
    }

    @Override
    public boolean isDefined(String name) throws IllegalArgumentException {
        Assert.hasText(name, "Variable name is null or empty.");
        return variableTypes.containsKey(name);
    }

    @Override
    public boolean is(String name, Class<?> type) throws IllegalArgumentException, UnknownVariableException {
        Assert.hasText(name, "Variable name is null or empty.");
        Assert.notNull(type, "Variable type cannot be null.");
        Class<?> variableType = variableTypes.get(name);
        return variableType != null && variableType.equals(type);
    }

    public boolean exists(ParserRuleContext ctx) {
        Class<?> variableType = variableTypes.get(ctx.getText());
        if (variableType == null) {
            throw new CompilationException("Variable " + ctx.getText() + " is undefined.", Location.from(ctx));
        }
        return true;
    }
}
