package com.questandglory.engine.expressions.integer;

import com.questandglory.engine.EngineFacade;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContextAwareIntegerExpression extends IntegerExpression {

    private final String variableName;

    @Override
    public Integer evaluate(EngineFacade context) {
        return context.state().getVariable(variableName);
    }
}
