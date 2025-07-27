package com.questandglory.engine.builtins;

import com.questandglory.engine.VariableTypeEnum;

import java.util.List;

public interface BuiltinFunction<T> {

    List<VariableTypeEnum> getParameters();

    VariableTypeEnum getReturnType();

    T execute(List<Object> parameters);

}
