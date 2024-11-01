package org.example.spacecatsmarket.service.mapper;

import org.example.spacecatsmarket.domain.Category;
import org.example.spacecatsmarket.dto.category.CategoryDto;
import org.example.spacecatsmarket.dto.category.CategoryEntry;
import org.example.spacecatsmarket.dto.category.CategoryEntryListDto;
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

    List<CategoryEntry> toCategoryEntry(List<Category> categories);

    @Named("toCategoryList")
    default List<Category> toCetagoryList(CategoryEntryListDto categoryEntryListDto) {
        return categoryEntryListDto.getCategoryEntries().stream().map(this::toCategory).toList();
    }


    @Named("toCategoryListDto")
    default CategoryEntryListDto toCategoryListDto(List<Category> categories) {
        return CategoryEntryListDto.builder().categoryEntries(toCategoryEntry(categories)).build();
    }


}
