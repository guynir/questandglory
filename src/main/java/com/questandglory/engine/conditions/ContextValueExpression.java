package com.questandglory.engine.conditions;


import com.questandglory.engine.EngineExecutionContext;

public class ContextValueExpression implements Expression<Object> {

    private final String variableName;

    public ContextValueExpression(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public Object getValue(EngineExecutionContext context) {
        return context.getVariable(variableName);
    }
}
