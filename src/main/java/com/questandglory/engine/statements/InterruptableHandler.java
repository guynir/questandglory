package com.questandglory.engine.statements;

@FunctionalInterface
public interface InterruptableHandler<T> {

    void handle(T facade) throws InterruptedException;

}
