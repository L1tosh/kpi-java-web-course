package org.example.spacecatsmarket.service.mapper;

import org.example.spacecatsmarket.common.Unit;
import org.example.spacecatsmarket.config.MappersTestConfiguration;
import org.example.spacecatsmarket.domain.Product;
import org.example.spacecatsmarket.dto.product.ProductDto;
import org.example.spacecatsmarket.dto.product.ProductEntry;
import org.example.spacecatsmarket.dto.product.ProductListDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest()
@Import(MappersTestConfiguration.class)
class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;

    private final Product PRODUCT = buildProduct();
    private final ProductDto PRODUCT_DTO = buildProductDto();
    private final ProductEntry PRODUCT_ENTRY = buildProductEntry();

    private ProductEntry buildProductEntry() {
        return ProductEntry.builder()
                .id(1L)
                .name("Anti-Gravity Star Yarn")
                .description("High-tech yarn that defies gravity, perfect for intergalactic crafting.")
                .price(49.99)
                .amount(500.0)
                .unit(Unit.METER.name()).build();
    }

    private ProductDto buildProductDto() {
        return ProductDto.builder()
                .name("Anti-Gravity Star Yarn")
                .description("High-tech yarn that defies gravity, perfect for intergalactic crafting.")
                .price(49.99)
                .amount(500.0)
                .unit(Unit.METER.name()).build();
    }

    private Product buildProduct() {
        return Product.builder()
                .id(1L)
                .name("Anti-Gravity Star Yarn")
                .description("High-tech yarn that defies gravity, perfect for intergalactic crafting.")
                .price(49.99)
                .amount(500.0)
                .unit(Unit.METER).build();
    }


    @Test
    void testToProductDto() {
        assertEquals(PRODUCT_DTO, productMapper.toProductDto(PRODUCT));
    }

    @Test
    void testMapProductDtoToProduct() {
        var product = buildProduct();
        product.setId(null);

        assertEquals(product, productMapper.toProduct(PRODUCT_DTO));
    }

    @Test
    void testMapProductEntryToProduct() {
        assertEquals(PRODUCT, productMapper.toProduct(PRODUCT_ENTRY));
    }

    @Test
    void testToProductEntry() {
        assertEquals(PRODUCT_ENTRY, productMapper.toProductEntry(PRODUCT));
    }

    @Test
    void testToProductListDto() {
        assertEquals(ProductListDto.builder().productEntries(List.of(PRODUCT_ENTRY)).build(),
                productMapper.toProductListDto(List.of(PRODUCT)));
    }

}
