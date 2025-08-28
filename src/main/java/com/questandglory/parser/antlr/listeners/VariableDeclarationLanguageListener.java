package com.questandglory.parser.antlr.listeners;

import com.questandglory.engine.statements.DefineVariableStatement;
import com.questandglory.language.variables.VariablesDefinition;
import com.questandglory.parser.LanguageFactory;
import com.questandglory.parser.antlr.LanguageBaseListener;
import com.questandglory.parser.antlr.LanguageParser;

/**
 * An ANTLR4 language listener that register variables as they appear in the script.
 *
 * @author Guy Raz Nir
 * @since 2025/08/15
 */
public class VariableDeclarationLanguageListener extends LanguageBaseListener {

    /**
     * Game state to register the variables at.
     */
    private final VariablesDefinition variables;

    /**
     * Factory to call an actual visitor to parse variable declaration definition.
     */
    private final LanguageFactory factory;

    /**
     * Class constructor.
     *
     * @param factory Language factory.
     */
    public VariableDeclarationLanguageListener(LanguageFactory factory) {
        this(new VariablesDefinition(), factory);
    }

    /**
     * Class constructor.
     */
    public VariableDeclarationLanguageListener(VariablesDefinition variables, LanguageFactory factory) {
        this.variables = variables != null ? variables : new VariablesDefinition();
        this.factory = factory;
    }

    /**
     * Register a new variable as soon as it is encountered.
     *
     * @param ctx Variable definition context.
     */
    @Override
    public void exitDefineVariableStatement(LanguageParser.DefineVariableStatementContext ctx) {
        // If there's an error processing variable definition -- do nothing.
        if (ctx.exception != null) {
            return;
        }

        // Parse the declaration into a statement.
        DefineVariableStatement statement = factory.defineVariable(ctx);
        String variableName = statement.getVariableName();

        if (variables.isVariableDefined(variableName)) {
            throw new IllegalStateException("Variable '" + variableName + "' is already defined.");
        }

        // Register the newly encountered variable.
        variables.registerVariable(variableName, statement.getJavaType());
    }

}
