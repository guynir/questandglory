package com.questandglory.engine.statements;

import com.questandglory.engine.EngineFacade;
import com.questandglory.engine.GameState;
import com.questandglory.engine.VariableTypeEnum;
import com.questandglory.engine.expressions.Expression;
import com.questandglory.parser.InternalCompilationErrorException;
import com.questandglory.parser.antlr.Location;
import lombok.Getter;

public class DefineVariableStatement extends Statement {

    @Getter
    private final String variableName;

    @Getter
    private final VariableTypeEnum variableType;

    @Getter
    private final Class<?> javaType;

    @Getter
    private final Expression<?> initialValue;

    public DefineVariableStatement(Location location,
                                   String variableName,
                                   VariableTypeEnum variableType,
                                   Expression<?> initialValue) {
        super(location);
        this.variableName = variableName;
        this.variableType = variableType;
        this.initialValue = initialValue;
        this.javaType = switch (variableType) {
            case STRING -> String.class;
            case INTEGER -> Integer.class;
            case BOOLEAN -> Boolean.class;
            default -> throw new InternalCompilationErrorException("Unsupported variable type: " + variableType);
        };

    }

    @Override
    protected void handleInternal(EngineFacade facade) throws InterruptedException {
        Object value = initialValue.evaluate(facade);

        GameState state = facade.state();
        state.registerVariable(variableName, javaType);
        state.setVariable(variableName, value);
    }
}
