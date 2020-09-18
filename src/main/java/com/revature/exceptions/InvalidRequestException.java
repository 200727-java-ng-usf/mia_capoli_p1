package com.revature.exceptions;

/**
 * An exception for when the request received is invalid for some reason.
 */
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}