package org.example.spacecatsmarket.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.spacecatsmarket.AbstractIt;
import org.example.spacecatsmarket.common.Unit;
import org.example.spacecatsmarket.config.MappersTestConfiguration;
import org.example.spacecatsmarket.domain.Product;
import org.example.spacecatsmarket.dto.product.ProductDto;
import org.example.spacecatsmarket.repository.CategoryRepository;
import org.example.spacecatsmarket.repository.ProductRepository;
import org.example.spacecatsmarket.repository.entity.CategoryEntity;
import org.example.spacecatsmarket.repository.entity.ProductEntity;
import org.example.spacecatsmarket.service.ProductService;
import org.example.spacecatsmarket.service.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Import(MappersTestConfiguration.class)
class ProductControllerIT extends AbstractIt {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private ProductEntity product;

    @BeforeEach
    void setUp() {
        reset(productService);
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    @WithMockUser
    void getProductById_shouldReturnProduct() throws Exception {
        saveProduct();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/{id}", product.getId())).andExpect(status().isOk()).andExpect(jsonPath("$.name").exists()).andExpect(jsonPath("$.name").value(product.getName())).andExpect(jsonPath("$.description").exists()).andExpect(jsonPath("$.description").value(product.getDescription())).andExpect(jsonPath("$.amount").exists()).andExpect(jsonPath("$.amount").value(product.getAmount())).andExpect(jsonPath("$.unit").exists()).andExpect(jsonPath("$.unit").value(product.getUnit()));
    }

    @Test
    @WithMockUser
    void getAllProducts_shouldReturnProductList() throws Exception {
        saveProduct();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products")).andExpect(status().isOk()).andExpect(jsonPath("$.productEntries").isArray()).andExpect(jsonPath("$.productEntries[0].name").exists()).andExpect(jsonPath("$.productEntries[0].name").value(product.getName())).andExpect(jsonPath("$.productEntries[0].description").value(product.getDescription())).andExpect(jsonPath("$.productEntries[0].amount").value(product.getAmount())).andExpect(jsonPath("$.productEntries[0].price").value(product.getPrice())).andExpect(jsonPath("$.productEntries[0].unit").value(product.getUnit()));
    }

    @Test
    @WithMockUser
    void createProduct_ShouldReturnCreatedProduct() throws Exception {
        var productDto = buildProductDto();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.description").value(productDto.getDescription()))
                .andExpect(jsonPath("$.amount").value(productDto.getAmount()))
                .andExpect(jsonPath("$.unit").value(productDto.getUnit()))
                .andExpect(jsonPath("$.price").value(productDto.getPrice()));
    }

    @Test
    @WithMockUser
    void updateProduct_shouldReturnUpdatedProduct() throws Exception {
        saveProduct();
        var productDto = buildProductDto();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/{id}", product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.description").value(productDto.getDescription()))
                .andExpect(jsonPath("$.amount").value(productDto.getAmount()))
                .andExpect(jsonPath("$.unit").value(productDto.getUnit()))
                .andExpect(jsonPath("$.price").value(productDto.getPrice()));
    }

    @Test
    @WithMockUser
    void deleteProduct_shouldReturnNoContent() throws Exception {
        saveProduct();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products/{id}", product.getId()))
                .andExpect(status().isNoContent());
    }


    private ProductDto buildProductDto() {
        return productMapper.toProductDto(buildProduct());
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

    private void saveProduct() {
        var categoryEntity = CategoryEntity.builder().name("some category").build();
        var productEntity = ProductEntity.builder()
                .id(1L)
                .name("Sun")
                .description("Shines well.")
                .price(15_999_999.99D)
                .category(categoryEntity)
                .amount(1)
                .unit(Unit.UNIT.name())
                .build();

        categoryRepository.save(categoryEntity);
        product = productRepository.save(productEntity);
    }
}
