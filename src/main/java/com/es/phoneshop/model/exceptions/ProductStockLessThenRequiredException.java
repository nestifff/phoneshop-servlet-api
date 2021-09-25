package com.es.phoneshop.model.exceptions;

public class ProductStockLessThenRequiredException extends Exception {

    public ProductStockLessThenRequiredException(String message) {
        super(message);
    }
}
