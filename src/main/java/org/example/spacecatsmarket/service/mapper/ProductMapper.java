package org.example.spacecatsmarket.service.mapper;

import org.example.spacecatsmarket.common.Unit;
import org.example.spacecatsmarket.domain.Product;
import org.example.spacecatsmarket.dto.product.ProductDto;
import org.example.spacecatsmarket.dto.product.ProductEntry;
import org.example.spacecatsmarket.dto.product.ProductListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "unit", source = "unit", qualifiedByName = "unitToString")
    @Mapping(target = "price", source = "price")
    ProductDto toProductDto(Product product);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "unit", source = "unit", qualifiedByName = "unitToString")
    @Mapping(target = "price", source = "price")
    ProductEntry toProductEntry(Product product);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "unit", source = "unit", qualifiedByName = "stringToUnit")
    @Mapping(target = "price", source = "price")
    Product toProduct(ProductDto productDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "unit", source = "unit", qualifiedByName = "stringToUnit")
    @Mapping(target = "price", source = "price")
    Product toProduct(ProductEntry productEntry);


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

}
