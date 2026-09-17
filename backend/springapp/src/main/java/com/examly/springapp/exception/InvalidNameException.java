
package com.examly.springapp.exception;

public class InvalidNameException extends RuntimeException {

    public InvalidNameException(String message) {
        super(message);
    }

    public InvalidNameException() {
        super("Name must contain only alphabets and spaces");
    }
}

