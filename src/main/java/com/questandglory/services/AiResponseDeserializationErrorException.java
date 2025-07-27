package com.questandglory.services;

public class AiResponseDeserializationErrorException extends RuntimeException {


    public AiResponseDeserializationErrorException(String message) {
        super(message);
    }

    public AiResponseDeserializationErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
