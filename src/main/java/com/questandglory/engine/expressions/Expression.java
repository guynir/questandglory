package com.questandglory.engine.expressions;

import com.questandglory.engine.EngineFacade;

public interface Expression<T> {

    T evaluate(EngineFacade context);

    Class<T> getType();

}
