package com.questandglory.engine.expressions.bool;


import com.questandglory.engine.EngineFacade;
import lombok.Getter;

public class BooleanVariableExpression extends BooleanExpression {

    @Getter
    private final String variableName;

    public BooleanVariableExpression(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public Boolean evaluate(EngineFacade context) {
        return context.state().getVariable(variableName);
    }
}
