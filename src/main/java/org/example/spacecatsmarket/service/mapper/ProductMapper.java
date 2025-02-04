package org.example.spacecatsmarket.service.mapper;

import org.example.spacecatsmarket.common.Unit;
import org.example.spacecatsmarket.domain.Product;
import org.example.spacecatsmarket.dto.product.ProductDto;
import org.example.spacecatsmarket.dto.product.ProductEntry;
import org.example.spacecatsmarket.dto.product.ProductListDto;
import org.example.spacecatsmarket.repository.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "unit", source = "unit", qualifiedByName = "unitToString")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "category", source = "category", qualifiedByName = "toCategoryEntry")
    ProductDto toProductDto(Product product);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "unit", source = "unit", qualifiedByName = "unitToString")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "category", source = "category", qualifiedByName = "toCategoryEntry")
    ProductEntry toProductEntry(Product product);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "unit", source = "unit", qualifiedByName = "stringToUnit")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "category", source = "category", qualifiedByName = "toCategory")
    Product toProduct(ProductDto productDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "unit", source = "unit", qualifiedByName = "stringToUnit")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "category", source = "category")
    Product toProduct(ProductEntry productEntry);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "unit", source = "unit", qualifiedByName = "unitToString")
    @Mapping(target = "category", source = "category")
    ProductEntity toProductEntity(Product product);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "unit", source = "unit", qualifiedByName = "stringToUnit")
    @Mapping(target = "category", source = "category", qualifiedByName = "toCategoryFromCategoryEntity")
    Product toProduct(ProductEntity productEntity);

    List<Product> toProductList(List<ProductEntity> productEntities);

    @Named("unitToString")
    default String stringToUnit(Unit unit) {
        return unit != null ? unit.name() : null;
    }

    @Named("stringToUnit")
    default Unit stringToUnit(String unit) {
        return unit != null ? Unit.valueOf(unit) : null;
    }

    default ProductListDto toProductListDto(List<Product> products) {
        return ProductListDto.builder().productEntries(toProductEntry(products)).build();
    }

    List<ProductEntry> toProductEntry(List<Product> products);

    @Mapping(target = "id", ignore = true)
    void updateProduct(Product source, @MappingTarget Product target);

}
