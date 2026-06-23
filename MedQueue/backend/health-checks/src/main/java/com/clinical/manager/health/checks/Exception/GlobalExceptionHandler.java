package com.clinical.manager.health.checks.Exception;


import java.util.HashMap;
import java.util.Map;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 * Handles all application exceptions globally.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     * Handles resource not found errors.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse>
    handleResourceNotFound(
            ResourceNotFoundException ex) {

        ApiErrorResponse error =
                new ApiErrorResponse(

                        false,

                        ex.getMessage(),

                        HttpStatus.NOT_FOUND.value(),

                        LocalDateTime.now()
                );

        return new ResponseEntity<>(
                error,
                HttpStatus.NOT_FOUND
        );
    }

    /*
     * Handles bad request errors.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse>
    handleBadRequest(
            BadRequestException ex) {

        ApiErrorResponse error =
                new ApiErrorResponse(

                        false,

                        ex.getMessage(),

                        HttpStatus.BAD_REQUEST.value(),

                        LocalDateTime.now()
                );

        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST
        );
    }

    /*
     * Handles access denied errors.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse>
    handleAccessDenied(
            AccessDeniedException ex) {

        ApiErrorResponse error =
                new ApiErrorResponse(

                        false,

                        "Access Denied",

                        HttpStatus.FORBIDDEN.value(),

                        LocalDateTime.now()
                );

        return new ResponseEntity<>(
                error,
                HttpStatus.FORBIDDEN
        );
    }

    /*
     * Handles validation errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>>
    handleValidationErrors(
            MethodArgumentNotValidException ex
    ) {

        Map<String, String> errors =
                new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->

                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return new ResponseEntity<>(
                errors,
                HttpStatus.BAD_REQUEST
        );
    }

    /*
     * Handles all unhandled exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse>
    handleGeneralException(Exception ex) {

        ApiErrorResponse error =
                new ApiErrorResponse(

                        false,

                        ex.getMessage(),

                        HttpStatus.INTERNAL_SERVER_ERROR.value(),

                        LocalDateTime.now()
                );

        return new ResponseEntity<>(

                error,

                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
