package org.example.spacecatsmarket.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spacecatsmarket.domain.Category;
import org.example.spacecatsmarket.service.CategoryService;
import org.example.spacecatsmarket.service.exception.CategoryNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final List<Category> categories = buildAllCategoriesMock();

    @Override
    public Category getCategoryById(Integer categoryId) {
        return Optional.of(categories.stream()
                        .filter(category -> category.getId().equals(categoryId))
                        .findFirst())
                .get()
                .orElseThrow(() -> {
                    log.info("Category with id {} not found in mock", categoryId);
                    return new CategoryNotFoundException(categoryId);
                });
    }

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    private List<Category> buildAllCategoriesMock() {
        return List.of(
                Category.builder().id(1).name("Crafting Supplies").build(),
                Category.builder().id(2).name("Zero-Gravity Items").build(),
                Category.builder().id(3).name("Beverages").build(),
                Category.builder().id(4).name("Nutritional Supplies").build(),
                Category.builder().id(5).name("Pet Supplies").build(),
                Category.builder().id(6).name("Intergalactic Herbs").build()
        );
    }
}

