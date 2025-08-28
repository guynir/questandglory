package com.questandglory;

/**
 * A general-purpose exception indicating that a requested resource was not found.
 *
 * @author Guy Raz Nir
 * @since 2025/08/27
 */
public class ResourceNotFoundException extends ApplicationException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
