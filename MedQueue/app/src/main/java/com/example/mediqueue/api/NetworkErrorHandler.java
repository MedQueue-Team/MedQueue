package com.example.mediqueue.api;


import java.io.IOException;
import java.net.SocketTimeoutException;
import retrofit2.HttpException;

/**
 * Centralized error handler for network operations
 */
public class NetworkErrorHandler {
    public static String handleError(Throwable t) {
        if (t instanceof SocketTimeoutException) {
            return "Connection timeout. Please try again.";
        } else if (t instanceof IOException) {
            return "Network error. Please check your internet connection.";
        } else if (t instanceof HttpException) {
            int code = ((HttpException) t).code();
            switch (code) {
                case 401: return "Unauthorized. Please login again.";
                case 403: return "Access forbidden.";
                case 404: return "Requested resource not found.";
                case 500: return "Internal server error. Please try again later.";
                default: return "Unexpected error occurred (Code: " + code + ")";
            }
        } else {
            return "An unknown error occurred: " + t.getMessage();
        }
    }
}
