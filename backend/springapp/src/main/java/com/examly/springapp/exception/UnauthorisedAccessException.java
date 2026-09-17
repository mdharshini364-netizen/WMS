
package com.examly.springapp.exception;

public class UnauthorisedAccessException extends RuntimeException {

    public UnauthorisedAccessException(String message) {
        super(message);
    }

    public UnauthorisedAccessException() {
        super("You are not authorized to perform this action");
    }
}

