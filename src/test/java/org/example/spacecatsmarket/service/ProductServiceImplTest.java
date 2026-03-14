package org.example.spacecatsmarket.service;

import org.example.spacecatsmarket.AbstractIt;
import org.example.spacecatsmarket.common.Unit;
import org.example.spacecatsmarket.domain.Product;
import org.example.spacecatsmarket.repository.CategoryRepository;
import org.example.spacecatsmarket.repository.ProductRepository;
import org.example.spacecatsmarket.repository.entity.CategoryEntity;
import org.example.spacecatsmarket.repository.entity.ProductEntity;
import org.example.spacecatsmarket.service.exception.PersistenceException;
import org.example.spacecatsmarket.service.exception.ProductNotFoundException;
import org.example.spacecatsmarket.service.mapper.ProductMapper;
import org.hibernate.exception.JDBCConnectionException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceImplTest extends AbstractIt {

    @Autowired
    private ProductMapper productMapper;

    @SpyBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryRepository categoryRepository;

    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        var categoryEntity = CategoryEntity.builder().name("some category").build();
        categoryRepository.save(categoryEntity);
        this.productEntity = productRepository.save(ProductEntity.builder()
                .id(1L)
                .name("Sun")
                .description("Shines well.")
                .price(15_999_999.99D)
                .category(categoryEntity)
                .amount(1)
                .unit(Unit.UNIT.name()).build());
    }

    @AfterEach
    void cleanUp() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    @Order(1)
    void createProduct_nonExistingProduct_shouldAddAndReturnProduct() {
        var newProduct = Product.builder().name("cosmo").description("test").unit(Unit.UNIT).price(1D).amount(1.1).build();

        var createdProduct = productService.createProduct(newProduct);

        assertNotNull(createdProduct.getId());
        assertEquals(newProduct.getName(), createdProduct.getName());
    }

    @Test
    void getAllProducts_shouldReturnAllProducts() {
        var products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(1, products.size());
    }

    @Test
    @Order(3)
    void getProductById_existingId_shouldReturnProduct() {
        var productById = productService.getProductById(productEntity.getId());

        assertNotNull(productById);
        assertEquals(productEntity.getName(), productById.getName());
    }

    @Test
    void getProductById_nonExistingId_shouldThrowException() {
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productEntity.getId() + 3));
    }

    @Test
    void updateProduct_existingId_shouldUpdateAndReturnProduct() {

        productEntity.setName(productEntity.getName() + "test");

        var result = productService.updateProduct(productEntity.getId(), productMapper.toProduct(productEntity));

        assertEquals(productEntity.getName(), result.getName());
        assertEquals(productEntity.getDescription(), result.getDescription());
        assertEquals(productEntity.getPrice(), result.getPrice());
    }

    @Test
    void updateProduct_nonExistingId_shouldThrowException() {
        var id = productEntity.getId() + 3;

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> productService.updateProduct(id, productMapper.toProduct(productEntity)));

        assertEquals("Product with id %d not found".formatted(id), exception.getMessage());
    }

    @Test
    void deleteProduct_existingProduct_shouldRemoveProduct() {
        Long productId = productEntity.getId();

        productService.deleteProduct(productId);

        assertFalse(productRepository.existsById(productId), "The product has not been removed from the database.");

    }

    @Test
    void deleteProduct_nonExistingProduct_shouldLogInfo() {
        try {
            productService.deleteProduct(productEntity.getId() + 3);
        } catch (Exception exception) {
            fail("Attempt to delete a non-existing product should not throw an exception.");
        }
    }

    @Test
    void shouldThrowPersistenceException() {
        var product = productMapper.toProduct(productEntity);

        when(productService.createProduct(product)).thenThrow(JDBCConnectionException.class);
        assertThrows(PersistenceException.class, () -> productService.createProduct(product));
    }
}
