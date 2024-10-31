package org.example.spacecatsmarket.service.exception;

public class CategoryNotFoundException extends RuntimeException {

    private static final String CATEGORY_NOT_FOUND_MESSAGE = "Category with id %s not found";

    public CategoryNotFoundException(Integer categoryId) {
        super(CATEGORY_NOT_FOUND_MESSAGE.formatted(categoryId));
    }
}
