package com.smartstore.erp.exception;

public class ProductInUseException extends RuntimeException {

    public ProductInUseException(String message) {
        super(message);
    }
}