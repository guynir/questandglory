package com.questandglory.engine;

import java.util.HashMap;
import java.util.Map;

public class TransientEngineState implements ExecutionState {
    private final Map<String, Object> objectsMap = new HashMap<>();

    @Override
    public void set(String objectId, Object objectValue) {
        objectsMap.put(objectId, objectValue);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String objectId) {
        return (T) objectsMap.get(objectId);
    }
}
