package org.example.spacecatsmarket.service.exception;

public class ProductCreatedException extends RuntimeException {

    private static final String PRODUCT_CREATED_MESSAGE = "Product with name \"%s\" and description \"%s\" already exist";

    public ProductCreatedException(String name, String description) {
        super(PRODUCT_CREATED_MESSAGE.formatted(name, description));
    }
}
