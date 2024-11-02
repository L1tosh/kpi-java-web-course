package org.example.spacecatsmarket.service;

import org.example.spacecatsmarket.common.Unit;
import org.example.spacecatsmarket.domain.Product;
import org.example.spacecatsmarket.service.exception.ProductCreatedException;
import org.example.spacecatsmarket.service.exception.ProductNotFoundException;
import org.example.spacecatsmarket.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ProductServiceImpl.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    private final Long NON_EXISTING_ID = Long.MAX_VALUE;

    private final Product EXISTING_PRODUCT = buildProduct();

    private Product buildProduct() {
        return Product.builder()
                .id(1L)
                .name("Anti-Gravity Star Yarn")
                .description("High-tech yarn that defies gravity, perfect for intergalactic crafting.")
                .price(49.99)
                .amount(500.0)
                .unit(Unit.METER).build();
    }

    private Product buildNewProduct() {
        return Product.builder()
                .name("Start №42")
                .description("Very beauty. That all...")
                .price(99.99)
                .amount(1.0)
                .unit(Unit.UNIT).build();
    }

    @Test
    @Order(1)
    void createProduct_nonExistingProduct_shouldAddAndReturnProduct() {
        Product newProduct = buildNewProduct();

        Product createdProduct = productService.createProduct(newProduct);

        assertNotNull(createdProduct.getId());
        assertEquals(newProduct.getName(), createdProduct.getName());
    }

    @Test
    @Order(2)
    void createProduct_existingProduct_shouldThrowException() {
        assertThrows(ProductCreatedException.class,
                () -> productService.createProduct(EXISTING_PRODUCT));
    }

    @Test
    void getAllProducts_shouldReturnAllProducts() {
        var products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(3, products.size());
    }

    @Test
    @Order(3)
    void getProductById_existingId_shouldReturnProduct() {
        var product = productService.getProductById(EXISTING_PRODUCT.getId());

        assertNotNull(product);
        assertEquals(EXISTING_PRODUCT.getName(), product.getName());
    }

    @Test
    void getProductById_nonExistingId_shouldThrowException() {
        assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(NON_EXISTING_ID));
    }

    @Test
    void updateProduct_existingId_shouldUpdateAndReturnProduct() {
        var updatedProduct = buildNewProduct();

        var result = productService.updateProduct(1L, updatedProduct);

        assertEquals(updatedProduct.getName(), result.getName());
        assertEquals(updatedProduct.getDescription(), result.getDescription());
        assertEquals(updatedProduct.getPrice(), result.getPrice());
    }

    @Test
    void updateProduct_nonExistingId_shouldThrowException() {
        var updatedProduct = buildNewProduct();

        assertThrows(ProductNotFoundException.class,
                () -> productService.updateProduct(NON_EXISTING_ID, updatedProduct));
    }

    @Test
    void deleteProduct_existingProduct_shouldRemoveProduct() {
        Long productId = EXISTING_PRODUCT.getId();

        assertTrue(productService.getAllProducts()
                .stream()
                .anyMatch(p -> p.getId().equals(productId)));

        productService.deleteProduct(productId);

        assertFalse(productService.getAllProducts().stream()
                .anyMatch(p -> p.getId().equals(productId)));
    }

    @Test
    void deleteProduct_nonExistingProduct_shouldLogInfo() {
        assertDoesNotThrow(() -> productService.deleteProduct(NON_EXISTING_ID),
                "Attempt to delete a non-existing product should not throw an exception.");
    }
}
