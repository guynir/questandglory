package com.questandglory.language.compiler;

import com.questandglory.language.variables.VariablesDefinition;
import com.questandglory.parser.CompilationException;
import com.questandglory.parser.antlr.Location;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Set of helper functions required during compilation stage.
 *
 * @author Guy Raz Nir
 * @since 2025/08/25
 */
public class CompilerHelper {

    /**
     * Variables definition.
     */
    private final VariablesDefinition variables;

    public CompilerHelper(VariablesDefinition variables) {
        this.variables = variables;
    }

    /**
     * Assert that a given variable exists.
     *
     * @param ctx An ANTLR rule context holding variable name.
     */
    public void assertVariableExist(ParserRuleContext ctx) throws CompilationException {
        String variableName = ctx.getText();
        if (variables.isVariableDefined(variableName)) {
            throw new CompilationException("Variable '" + variableName + "' is not defined.", Location.from(ctx));
        }
    }

    /**
     * Assert that a variable exist and is of a given type.
     *
     * @param ctx  Rule context that contains variable name.
     * @param type Expected type of variable.
     * @throws CompilationException If variable does not exist or is not of expected type.
     */
    public boolean assertVariableType(ParserRuleContext ctx, Class<?> type) throws CompilationException {
        String variableName = ctx.getText();
        Class<?> clazz = variables.getVariableType(variableName);

        if (clazz == null) {
            throw new CompilationException("Variable '" + variableName + "' is not defined.", Location.from(ctx));
        }

        if (!type.equals(clazz)) {
            String message = "Variable '%s' is %s, but is expected to be %s.".formatted(variableName,
                    type.getSimpleName(),
                    clazz.getSimpleName());
            throw new CompilationException(message, Location.from(ctx));
        }

        return true;
    }
}
