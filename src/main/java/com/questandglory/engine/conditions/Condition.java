package com.questandglory.engine.conditions;

import com.questandglory.engine.EngineExecutionContext;

public interface Condition {

    boolean evaluate(EngineExecutionContext context);

}
