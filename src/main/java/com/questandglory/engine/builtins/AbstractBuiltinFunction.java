package com.questandglory.engine.builtins;

import com.questandglory.engine.VariableTypeEnum;

import java.util.Iterator;
import java.util.List;

public abstract class AbstractBuiltinFunction<T> implements BuiltinFunction<T> {

    protected final VariableTypeEnum returnType;
    protected final List<VariableTypeEnum> parameterTypes;

    public AbstractBuiltinFunction(VariableTypeEnum returnType, List<VariableTypeEnum> parameterTypes) {
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    public AbstractBuiltinFunction(VariableTypeEnum returnType, VariableTypeEnum... parameterTypes) {
        this.parameterTypes = List.of(parameterTypes);
        this.returnType = returnType;
    }

    @Override
    public VariableTypeEnum getReturnType() {
        return returnType;
    }

    @Override
    public List<VariableTypeEnum> getParameters() {
        return parameterTypes;
    }

    @Override
    public T execute(List<Object> parameters) {
        if (parameters.size() != parameterTypes.size()) {
            throw new IllegalArgumentException("Incorrect number of parameters. Required "
                    + parameters.size()
                    + " parameters, but found "
                    + parameterTypes.size()
                    + ".");
        }

        Iterator<VariableTypeEnum> variableTypeItr = parameterTypes.iterator();
        Iterator<Object> parameterItr = parameters.iterator();
        while (variableTypeItr.hasNext() && parameterItr.hasNext()) {
            VariableTypeEnum variableType = variableTypeItr.next();
            Object parameter = parameterItr.next();
            Class<?> javaType;

            switch (variableType) {
                case STRING -> javaType = String.class;
                case INTEGER -> javaType = Integer.class;
                case BOOLEAN -> javaType = Boolean.class;
                default -> throw new IllegalArgumentException("Unknown variable type -- " + variableType.name());
            }
            if (!javaType.isInstance(parameter)) {
                throw new IllegalArgumentException("Incorrect variable type -- " + variableType.name());
            }
        }

        return executeInternal(parameters);
    }

    protected abstract T executeInternal(List<Object> parameters);
}
