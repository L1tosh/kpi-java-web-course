package org.example.spacecatsmarket.service.mapper;

import org.example.spacecatsmarket.domain.Category;
import org.example.spacecatsmarket.dto.category.CategoryDto;
import org.example.spacecatsmarket.dto.category.CategoryEntry;
import org.example.spacecatsmarket.dto.category.CategoryListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "name", source = "name")
    CategoryDto toCategoryDto(Category category);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CategoryEntry toCategoryEntry(Category category);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Category toCategory(CategoryEntry categoryEntry);

    @Mapping(target = "name", source = "name")
    Category toCategory(CategoryDto categoryEntry);

    List<CategoryEntry> toCategoryEntry(List<Category> categories);

    @Named("toCategoryList")
    default List<Category> toCetagoryList(CategoryListDto categoryListDto) {
        return categoryListDto.getCategoryEntries().stream().map(this::toCategory).toList();
    }

    @Named("toCategoryListDto")
    default CategoryListDto toCategoryListDto(List<Category> categories) {
        return CategoryListDto.builder().categoryEntries(toCategoryEntry(categories)).build();
    }


}
