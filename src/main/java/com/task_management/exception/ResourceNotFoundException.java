package com.task_management.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

// we are creating our own exception.
// we use this exception when something that the user requested, doesn't exist.
