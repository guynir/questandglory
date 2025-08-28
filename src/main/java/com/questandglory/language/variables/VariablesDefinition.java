package com.questandglory.language.variables;

import lombok.Setter;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Maintains definitions of variables.
 * </p>
 * This implementation also supports nested scopes. If a child scope is associated with this definition set, variables
 * that are not registered within the scope of this instance are delegated to the child.
 *
 * @author Guy Raz Nir
 * @since 2025/08/26
 */
public class VariablesDefinition {

    /**
     * Maintains all variables definitions within the current scope.
     */
    private final Map<String, Class<?>> types = new HashMap<>();

    /**
     * Optional child scope.
     */
    @Setter
    private VariablesDefinition childScope = null;

    /**
     * Class constructor.
     */
    public VariablesDefinition() {
    }

    /**
     * Remove any child scope, if any.
     */
    public void clearChildScope() {
        this.childScope = null;
    }

    /**
     * Register a new variable.
     *
     * @param variableName Variable name.
     * @param variableType Variable type.
     * @throws VariableAlreadyDefinedException If variable is already defined.
     */
    public void registerVariable(String variableName, Class<?> variableType) throws VariableAlreadyDefinedException {
        Assert.hasText(variableName, "Variable name cannot be null or empty.");
        Assert.notNull(variableType, "Variable type cannot be null.");
        if (isVariableDefined(variableName)) {
            throw new VariableAlreadyDefinedException("Variable '"
                    + variableName
                    + "' is already defined within this scope.");
        }
        types.putIfAbsent(variableName, variableType);
    }

    /**
     * Test if a given variable is defined.
     *
     * @param variableName Variable to test.
     * @return {@code true} if variable is defined, {@code false} otherwise.
     */
    public boolean isVariableDefined(String variableName) {
        return getVariableType(variableName) != null;
    }

    /**
     * Fetch variable type.
     *
     * @param variableName Variable name.
     * @return Type of variable or {@code null} if variable is not defined.
     */
    public Class<?> getVariableType(String variableName) {
        Assert.hasText(variableName, "Variable name must not be null or empty.");

        // Fetch variable definition either from this scope or drill in if variable is not defined and we have
        // a child scope.
        Class<?> type = types.get(variableName);
        return type != null ? type : childScope != null ? childScope.getVariableType(variableName) : null;
    }

    /**
     * Test if a given variable exist and is of a given type.
     *
     * @param variableName Variable name to test.
     * @param variableType Variable type to test for.
     * @return {@code true} if variable is defined and is of <i>variableType</i>, {@code false} otherwise.
     */
    public boolean isVariableOfType(String variableName, Class<?> variableType) {
        Assert.notNull(variableType, "Variable type cannot be null.");
        Class<?> type = getVariableType(variableName);
        return type != null && type.equals(variableType);
    }


}
