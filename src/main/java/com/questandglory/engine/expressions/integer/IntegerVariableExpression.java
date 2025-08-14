package com.questandglory.engine.expressions.integer;


import com.questandglory.engine.EngineFacade;
import lombok.Getter;

public class IntegerVariableExpression extends IntegerExpression {

    @Getter
    private final String variableName;

    public IntegerVariableExpression(String variableName) {
        this.variableName = variableName;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Integer evaluate(EngineFacade context) {
        return context.state().getVariable(variableName);
    }
}
