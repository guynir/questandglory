package com.questandglory.language.variables;

public class VariableAlreadyDefinedException extends RuntimeException {
    public VariableAlreadyDefinedException(String message) {
        super(message);
    }
}
