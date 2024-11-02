package org.example.spacecatsmarket.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spacecatsmarket.common.Unit;
import org.example.spacecatsmarket.domain.Product;
import org.example.spacecatsmarket.service.ProductService;
import org.example.spacecatsmarket.service.exception.ProductCreatedException;
import org.example.spacecatsmarket.service.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
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
        boolean productExists = products.stream()
                .anyMatch(existingProduct -> existingProduct.getName().equals(product.getName()) &&
                        existingProduct.getDescription().equals(product.getDescription()));

        if (productExists) {
            log.info("Product with name {} and description {} already exists", product.getName(), product.getDescription());
            throw new ProductCreatedException(product.getName(), product.getDescription());
        }

        long newId = products.stream()
                .mapToLong(Product::getId)
                .max()
                .orElse(0) + 1;

        product.setId(newId);
        products.add(product);

        log.info("Product with id {} created", newId);
        return product;
    }

    @Override
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = getProductById(id);

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setAmount(updatedProduct.getAmount());
        existingProduct.setUnit(updatedProduct.getUnit());

        log.info("Product with id {} updated", id);
        return existingProduct;
    }

    @Override
    public void deleteProduct(Long id) {
        try {
            Product productToDelete = getProductById(id);

            products.remove(productToDelete);
            log.info("Product with id {} deleted", id);
        } catch (ProductNotFoundException ex) {
            log.info("attempt to delete a product with an ID {} that does not exist", id);
        }
    }

    private List<Product> buildAllProductsMock() {

        List<Product> list = new ArrayList<>();
        list.add(Product.builder()
                .id(1L)
                .name("Anti-Gravity Star Yarn")
                .description("High-tech yarn that defies gravity, perfect for intergalactic crafting.")
                .price(49.99)
                .amount(500.0)
                .unit(Unit.METER).build());

        list.add(Product.builder()
                .id(2L)
                .name("Cosmic Milk")
                .description("Premium milk collected from the stars, rich in cosmic nutrients.")
                .price(15.75)
                .amount(1.0)
                .unit(Unit.LITER).build());

        list.add(Product.builder()
                .id(3L)
                .name("Nebula Catnip")
                .description("Aromatic catnip harvested from the Nebula fields, perfect for space cats.")
                .price(12.30)
                .amount(100.0)
                .unit(Unit.GRAM).build());

        return list;
    }
}
