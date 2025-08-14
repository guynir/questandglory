package com.questandglory.parser;

public class InternalCompilationErrorException extends RuntimeException {

    public InternalCompilationErrorException(String message) {
        super(message);
    }

    public InternalCompilationErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
