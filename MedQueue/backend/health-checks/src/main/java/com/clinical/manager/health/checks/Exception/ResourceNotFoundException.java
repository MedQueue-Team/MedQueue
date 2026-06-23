package com.clinical.manager.health.checks.Exception;



/*
 * Thrown when requested resource
 * does not exist.
 */
public class ResourceNotFoundException
        extends RuntimeException {

    public ResourceNotFoundException(
            String message) {

        super(message);
    }
}
