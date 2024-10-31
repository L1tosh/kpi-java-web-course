package org.example.spacecatsmarket.service.mapper;

import org.example.spacecatsmarket.domain.Category;
import org.example.spacecatsmarket.dto.CategoryDto;
import org.example.spacecatsmarket.dto.CategoryEntry;
import org.example.spacecatsmarket.dto.CategoryEntryListDto;
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

    List<CategoryEntry> toCategoryEntry(List<Category> categories);

    @Named("toCategoryListDto")
    default CategoryEntryListDto toCategoryListDto(List<Category> categories) {
        return CategoryEntryListDto.builder().categoryEntries(toCategoryEntry(categories)).build();
    }
}