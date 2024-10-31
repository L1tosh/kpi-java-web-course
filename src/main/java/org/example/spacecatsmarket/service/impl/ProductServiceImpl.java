package org.example.spacecatsmarket.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spacecatsmarket.common.Unit;
import org.example.spacecatsmarket.domain.Category;
import org.example.spacecatsmarket.domain.Product;
import org.example.spacecatsmarket.service.CategoryService;
import org.example.spacecatsmarket.service.ProductService;
import org.example.spacecatsmarket.service.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CategoryService categoryService;
    private final List<Product> products = buildAllProductsMock();

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public Product getProductById(Long productId) {
        return Optional.of(products.stream().filter(product -> product.getId().equals(productId)).findFirst()).get().orElseThrow(() -> {
            log.info("Product with id {} not found in mock", productId);
            return new ProductNotFoundException(productId);
        });
    }

    @Override
    public Product createProduct(Product product) {
        if (product.getCategories() != null) {
            product.getCategories().forEach(category ->
                    categoryService.getCategoryById(category.getId())
            );
        }

        long newId = products.stream().mapToLong(Product::getId).max().orElse(0) + 1;
        product.setId(newId);

        products.add(product);

        log.info("Product with id {} created", newId);
        return product;
    }

    @Override
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = getProductById(id);

        if (updatedProduct.getCategories() != null) {
            updatedProduct.getCategories().forEach(category ->
                    categoryService.getCategoryById(category.getId())
            );
        }

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setCategories(updatedProduct.getCategories());
        existingProduct.setAmount(updatedProduct.getAmount());
        existingProduct.setUnit(updatedProduct.getUnit());

        log.info("Product with id {} updated", id);
        return existingProduct;
    }

    @Override
    public Product deleteProduct(Long id) {
        Product productToDelete = getProductById(id);
        products.remove(productToDelete);

        log.info("Product with id {} deleted", id);
        return productToDelete;
    }

    private List<Product> buildAllProductsMock() {
        List<Category> categories = List.of(
                Category.builder().id(1).name("Crafting Supplies").build(),
                Category.builder().id(2).name("Zero-Gravity Items").build(),
                Category.builder().id(3).name("Beverages").build(),
                Category.builder().id(4).name("Nutritional Supplies").build(),
                Category.builder().id(5).name("Pet Supplies").build(),
                Category.builder().id(6).name("Intergalactic Herbs").build()
        );


        List<Product> list = new ArrayList<>();
        list.add(Product.builder()
                .id(1L)
                .name("Anti-Gravity Yarn")
                .description("High-tech yarn that defies gravity, perfect for intergalactic crafting.")
                .price(49.99)
                .categories(List.of(categories.get(0), categories.get(1))) // Используем категории из списка
                .amount(500.0)
                .unit(Unit.METER).build());

        list.add(Product.builder()
                .id(2L)
                .name("Cosmic Milk")
                .description("Premium milk collected from the stars, rich in cosmic nutrients.")
                .price(15.75)
                .categories(List.of(categories.get(2), categories.get(3))) // Используем категории из списка
                .amount(1.0)
                .unit(Unit.LITER).build());

        list.add(Product.builder()
                .id(3L)
                .name("Nebula Catnip")
                .description("Aromatic catnip harvested from the Nebula fields, perfect for space cats.")
                .price(12.30)
                .categories(List.of(categories.get(4), categories.get(5))) // Используем категории из списка
                .amount(100.0)
                .unit(Unit.GRAM).build());

        return list;
    }
}
