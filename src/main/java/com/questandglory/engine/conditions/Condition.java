package com.questandglory.engine.conditions;

import com.questandglory.engine.EngineFacade;

public interface Condition {

    boolean evaluate(EngineFacade context);

}
