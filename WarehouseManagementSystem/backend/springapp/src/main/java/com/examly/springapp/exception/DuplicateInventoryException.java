
package com.examly.springapp.exception;

public class DuplicateInventoryException extends RuntimeException {

    public DuplicateInventoryException(String message) {
        super(message);
    }

    public DuplicateInventoryException() {
        super("Duplicate inventory record already exists");
    }
}

