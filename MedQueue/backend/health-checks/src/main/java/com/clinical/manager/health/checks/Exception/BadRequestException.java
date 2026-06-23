package com.clinical.manager.health.checks.Exception;



/*
 * Thrown when request data
 * is invalid.
 */
public class BadRequestException
        extends RuntimeException {

    public BadRequestException(
            String message) {

        super(message);
    }
}
