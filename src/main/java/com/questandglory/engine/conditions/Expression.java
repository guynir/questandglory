package com.questandglory.engine.conditions;

import com.questandglory.engine.EngineExecutionContext;

public interface Expression<T> {

    T getValue(EngineExecutionContext context);

}
