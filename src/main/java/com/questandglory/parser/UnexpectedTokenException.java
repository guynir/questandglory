package com.questandglory.parser;

public class UnexpectedTokenException extends RuntimeException {

    public UnexpectedTokenException() {
    }

    public UnexpectedTokenException(String message) {
        super(message);
    }
}
