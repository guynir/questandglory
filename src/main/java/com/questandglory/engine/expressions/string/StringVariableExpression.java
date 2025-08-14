package com.questandglory.engine.expressions.string;

import com.questandglory.engine.EngineFacade;

public class StringVariableExpression extends StringExpression {

    private final String variableName;

    public StringVariableExpression(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public String evaluate(EngineFacade facade) {
        return facade.state().getVariable(variableName);
    }
}
