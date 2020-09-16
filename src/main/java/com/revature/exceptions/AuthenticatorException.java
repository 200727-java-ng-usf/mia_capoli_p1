package com.revature.exceptions;

public class AuthenticatorException extends RuntimeException{
/**
*   AuthenticatorException: used for when a user or account cannot be found by the database, or they exist when attempting to be registered.
 */
    public AuthenticatorException(String message) {
        super(message);
    }

    public AuthenticatorException() {
        super("Authentication failed.");
    }
}
