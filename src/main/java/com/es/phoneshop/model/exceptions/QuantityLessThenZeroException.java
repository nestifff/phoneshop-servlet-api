package com.es.phoneshop.model.exceptions;

public class QuantityLessThenZeroException extends Exception {

    public QuantityLessThenZeroException(String message) {
        super(message);
    }
}
