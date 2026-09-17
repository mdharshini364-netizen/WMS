
package com.examly.springapp.exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String message) {
        super(message);
    }

    public InsufficientStockException(String productName, Integer requested, Integer available) {
        super(String.format("Insufficient stock for '%s'. Requested: %d, Available: %d",
                productName, requested, available));
    }
}

