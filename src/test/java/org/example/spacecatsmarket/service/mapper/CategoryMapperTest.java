package org.example.spacecatsmarket.service.mapper;

import org.example.spacecatsmarket.config.MappersTestConfiguration;
import org.example.spacecatsmarket.domain.Category;
import org.example.spacecatsmarket.dto.category.CategoryDto;
import org.example.spacecatsmarket.dto.category.CategoryEntry;
import org.example.spacecatsmarket.dto.category.CategoryListDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Import(MappersTestConfiguration.class)
class CategoryMapperTest {

    private final Category CATEGORY = Category.builder().id(1).name("Crafting Supplies").build();
    private final CategoryDto CATEGORY_DTO = CategoryDto.builder().name("Crafting Supplies").build();
    private final CategoryEntry CATEGORY_ENTRY = CategoryEntry.builder().id(1).name("Crafting Supplies").build();

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    void toCategoryDto() {
        assertEquals(CATEGORY_DTO, categoryMapper.toCategoryDto(CATEGORY));
    }

    @Test
    void toCategoryEntry() {
        assertEquals(CATEGORY_ENTRY, categoryMapper.toCategoryEntry(CATEGORY));
    }

    @Test
    void mapCategoryDtoToCategory() {
        var expected = Category.builder().name("Crafting Supplies").build();

        assertEquals(expected, categoryMapper.toCategory(CATEGORY_DTO));
    }

    @Test
    void mapCategoryEntryToCategory() {
        assertEquals(CATEGORY, categoryMapper.toCategory(CATEGORY_ENTRY));
    }

    @Test
    void toCategoryEntryList() {
        var expected = CategoryListDto.builder()
                .categoryEntries(List.of(CATEGORY_ENTRY))
                .build();

        assertEquals(expected,
                categoryMapper.toCategoryListDto(List.of(CATEGORY)));
    }

}
