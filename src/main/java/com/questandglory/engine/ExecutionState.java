package com.questandglory.engine;

public interface ExecutionState {

    void set(String objectId, Object objectValue);

    <T> T get(String objectId);

}
