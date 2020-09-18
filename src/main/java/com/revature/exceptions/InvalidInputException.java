package com.revature.exceptions;

public class InvalidInputException extends RuntimeException{
    /**
     *   InvalidInputException: used as a catch all for inputs that do not make sense for the applicaiton.
     */
    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException() {
        super("Invalid input.");
    }
}