package com.es.phoneshop.model.exceptions;

public class ProductNotFoundInDaoException extends Exception {

    public ProductNotFoundInDaoException(String message) {
        super(message);
    }
}
