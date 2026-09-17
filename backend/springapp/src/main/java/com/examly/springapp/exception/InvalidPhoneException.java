
package com.examly.springapp.exception;

public class InvalidPhoneException extends RuntimeException {

    public InvalidPhoneException(String message) {
        super(message);
    }

    public InvalidPhoneException() {
        super("Phone number must be exactly 10 digits");
    }
}

