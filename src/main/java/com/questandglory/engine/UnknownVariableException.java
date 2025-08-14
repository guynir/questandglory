package com.questandglory.engine;

/**
 * This exception is thrown when an unknown variable is encountered during expression evaluation.
 *
 * @author Guy Raz Nir
 * @since 2025/07/31
 */
public class UnknownVariableException extends RuntimeException {

    public UnknownVariableException(String message) {
        super(message);
    }

    public UnknownVariableException(String message, Throwable cause) {
        super(message, cause);
    }
}
