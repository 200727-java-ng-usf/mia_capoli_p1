package com.revature.exceptions;

/**
 * An exception for when the resource does not exist.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("No resources found using the specified criteria.");
    }

}
