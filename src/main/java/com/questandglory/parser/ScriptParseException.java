package com.questandglory.parser;

import lombok.Getter;

import java.util.List;

public class ScriptParseException extends RuntimeException {

    @Getter
    private final List<ScriptError> errors;

    public ScriptParseException(String message) {
        super(message);
        this.errors = List.of();
    }

    public ScriptParseException(String message, Throwable cause) {
        super(message, cause);
        this.errors = List.of();
    }

    public ScriptParseException(String message, List<ScriptError> errors) {
        super(message);
        this.errors = errors;
    }

}
