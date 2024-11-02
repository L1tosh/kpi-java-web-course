package org.example.spacecatsmarket.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.spacecatsmarket.common.Unit;
import org.example.spacecatsmarket.config.MappersTestConfiguration;
import org.example.spacecatsmarket.domain.Product;
import org.example.spacecatsmarket.dto.product.ProductDto;
import org.example.spacecatsmarket.service.ProductService;
import org.example.spacecatsmarket.service.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Import(MappersTestConfiguration.class)
class ProductControllerIT {

    private final Product PRODUCT= buildProduct();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        reset(productService);
    }


    @Test
    void getProductById_shouldReturnProduct() throws Exception {
        Long productId = 1L;

        when(productService.getProductById(1L))
                .thenReturn(PRODUCT);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.name").value(PRODUCT.getName()))
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.description").value(PRODUCT.getDescription()))
                .andExpect(jsonPath("$.amount").exists())
                .andExpect(jsonPath("$.amount").value(PRODUCT.getAmount()))
                .andExpect(jsonPath("$.unit").exists())
                .andExpect(jsonPath("$.unit").value(PRODUCT.getUnit().name()));
    }

    @Test
    void getAllProducts_shouldReturnProductList() throws Exception {
        when(productService.getAllProducts())
                .thenReturn(List.of(PRODUCT));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productEntries").isArray())
                .andExpect(jsonPath("$.productEntries[0].name").exists())
                .andExpect(jsonPath("$.productEntries[0].name").value(PRODUCT.getName()))
                .andExpect(jsonPath("$.productEntries[0].description").value(PRODUCT.getDescription()))
                .andExpect(jsonPath("$.productEntries[0].amount").value(PRODUCT.getAmount()))
                .andExpect(jsonPath("$.productEntries[0].price").value(PRODUCT.getPrice()))
                .andExpect(jsonPath("$.productEntries[0].unit").value(PRODUCT.getUnit().name()));
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() throws Exception {
        var PRODUCT_DTO = buildProductDto();

        when(productService.createProduct(productMapper.toProduct(PRODUCT_DTO)))
                .thenReturn(PRODUCT);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PRODUCT_DTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(PRODUCT.getName()))
                .andExpect(jsonPath("$.description").value(PRODUCT.getDescription()))
                .andExpect(jsonPath("$.amount").value(PRODUCT.getAmount()))
                .andExpect(jsonPath("$.unit").value(PRODUCT.getUnit().name()))
                .andExpect(jsonPath("$.price").value(PRODUCT.getPrice()));
    }

    @Test
    void updateProduct_shouldReturnUpdatedProduct() throws Exception {
        var PRODUCT_DTO = buildProductDto();
        long productId = 1L;

        when(productService.updateProduct(productId, productMapper.toProduct(PRODUCT_DTO)))
                .thenReturn(PRODUCT);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PRODUCT_DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(PRODUCT.getName()))
                .andExpect(jsonPath("$.description").value(PRODUCT.getDescription()))
                .andExpect(jsonPath("$.amount").value(PRODUCT.getAmount()))
                .andExpect(jsonPath("$.unit").value(PRODUCT.getUnit().name()))
                .andExpect(jsonPath("$.price").value(PRODUCT.getPrice()));
    }

    @Test
    void deleteProduct_shouldReturnNoContent() throws Exception {
        Long productId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products/{id}", productId))
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

}
