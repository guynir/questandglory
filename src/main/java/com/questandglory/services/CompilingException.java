package com.questandglory.services;

import com.questandglory.parser.CompilationErrors;

public class CompilingException extends RuntimeException {

    private final CompilationErrors errors;

    public CompilingException(CompilationErrors errors) {
        this.errors = errors;
    }

    public CompilingException(String message) {
        this(message, new CompilationErrors());
    }

    public CompilingException(String message, CompilationErrors errors) {
        super(message);
        this.errors = errors;
    }

    public CompilingException(String message, Throwable cause, CompilationErrors errors) {
        super(message, cause);
        this.errors = errors;
    }

    public CompilingException(Throwable cause, CompilationErrors errors) {
        super(cause);
        this.errors = errors;
    }

}
