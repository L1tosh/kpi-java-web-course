package org.example.spacecatsmarket.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.spacecatsmarket.common.Unit;
import org.example.spacecatsmarket.domain.Category;
import org.example.spacecatsmarket.domain.Product;
import org.example.spacecatsmarket.service.ProductService;
import org.example.spacecatsmarket.service.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final List<Product> products = buildAllProductsMock();

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public Product getProductById(Long productId) {
        return Optional.of(products.stream()
                        .filter(product -> product.getId().equals(productId))
                        .findFirst())
                .get()
                .orElseThrow(() -> {
                    log.info("Product withid {} not found in mock", productId);
                    return new ProductNotFoundException(productId);
                });
    }

    private List<Product> buildAllProductsMock() {
        return List.of(
                Product.builder()
                        .id(1L)
                        .name("Anti-Gravity Yarn")
                        .description("High-tech yarn that defies gravity, perfect for intergalactic crafting.")
                        .price(49.99)
                        .categories(List.of(
                                Category.builder().id(1).name("Crafting Supplies").build(),
                                Category.builder().id(2).name("Zero-Gravity Items").build()
                        ))
                        .amount(500.0)
                        .unit(Unit.METER)
                        .build(),

                Product.builder()
                        .id(2L)
                        .name("Cosmic Milk")
                        .description("Premium milk collected from the stars, rich in cosmic nutrients.")
                        .price(15.75)
                        .categories(List.of(
                                Category.builder().id(3).name("Beverages").build(),
                                Category.builder().id(4).name("Nutritional Supplies").build()
                        ))
                        .amount(1.0)
                        .unit(Unit.LITER)
                        .build(),

                Product.builder()
                        .id(3L)
                        .name("Nebula Catnip")
                        .description("Aromatic catnip harvested from the Nebula fields, perfect for space cats.")
                        .price(12.30)
                        .categories(List.of(
                                Category.builder().id(5).name("Pet Supplies").build(),
                                Category.builder().id(6).name("Intergalactic Herbs").build()
                        ))
                        .amount(100.0)
                        .unit(Unit.GRAM)
                        .build()
        );
    }
}
