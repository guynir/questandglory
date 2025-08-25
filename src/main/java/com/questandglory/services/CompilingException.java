package com.questandglory.services;

public class CompilingException extends RuntimeException {

    public CompilingException(String message) {
        super(message);
    }

    public CompilingException(String message, Throwable cause) {
        super(message, cause);
    }
}
