package org.example.spacecatsmarket.service;

import org.example.spacecatsmarket.domain.Category;
import org.example.spacecatsmarket.service.exception.CategoryNotFoundException;
import org.example.spacecatsmarket.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {CategoryServiceImpl.class})
class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;
    private final Integer NON_EXISTING_ID = Integer.MAX_VALUE;
    private final Category EXISTING_CATEGORY = buildCategory();

    private Category buildCategory() {
        return Category.builder().id(1).name("Crafting Supplies").build();
    }

    @Test
    void getCategoryById_existingCategory_shouldReturnCategory() {
        var category = categoryService.getCategoryById(EXISTING_CATEGORY.getId());

        assertEquals(EXISTING_CATEGORY, category);
    }

    @Test
    void getCategoryById_notExistingCategory_shouldReturnCategory() {
        assertThrows(CategoryNotFoundException.class,
                () -> categoryService.getCategoryById(NON_EXISTING_ID));
    }

    @Test
    void getAllCategories_shouldReturnAllCategories() {
        var categories = categoryService.getAllCategories();

        assertNotNull(categories);
        assertEquals(6, categories.size());
    }



}
