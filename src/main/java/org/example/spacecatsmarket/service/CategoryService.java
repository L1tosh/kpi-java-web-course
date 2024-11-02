package org.example.spacecatsmarket.service;

import org.example.spacecatsmarket.domain.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Integer categoryId);
    List<Category> getAllCategories();

}
