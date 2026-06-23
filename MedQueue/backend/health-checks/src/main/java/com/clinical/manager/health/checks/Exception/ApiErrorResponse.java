package com.clinical.manager.health.checks.Exception;



import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*
 * Standard API error response.
 */
@Getter
@Setter
@AllArgsConstructor
public class ApiErrorResponse {

    /*
     * Indicates request failure.
     */
    private boolean success;

    /*
     * Error message.
     */
    private String message;

    /*
     * HTTP status code.
     */
    private int status;

    /*
     * Error timestamp.
     */
    private LocalDateTime timestamp;
}
