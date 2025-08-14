package com.questandglory.parser;

import com.questandglory.parser.antlr.Location;
import lombok.Getter;

public class CompilationException extends RuntimeException {

    @Getter
    private final Location location;

    public CompilationException(String message, Location location) {
        super(message);
        this.location = location;
    }

    public CompilationException(String message, Throwable cause, Location location) {
        super(message, cause);
        this.location = location;
    }


}
