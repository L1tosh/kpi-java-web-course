package org.example.spacecatsmarket.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.spacecatsmarket.dto.product.ProductDto;
import org.example.spacecatsmarket.dto.product.ProductEntry;
import org.example.spacecatsmarket.dto.product.ProductListDto;
import org.example.spacecatsmarket.service.ProductService;
import org.example.spacecatsmarket.service.mapper.ProductMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productMapper.toProductDto(productService.getProductById(id)));
    }

    @GetMapping
    public ResponseEntity<ProductListDto> getAllProducts() {
        return ResponseEntity.ok(productMapper.toProductListDto(productService.getAllProducts()));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto productToSave) {
        var savedProduct = productService.createProduct(productMapper.toProduct(productToSave));
        return ResponseEntity
                .created(createProductUri(savedProduct.getId()))
                .body(productMapper.toProductDto(savedProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductEntry> updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid ProductDto updatedProduct) {

        var savedProduct = productService.updateProduct(id, productMapper.toProduct(updatedProduct));
        return ResponseEntity.ok(productMapper.toProductEntry(savedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    private URI createProductUri(Long id) {
        return URI.create(String.format("/api/v1/products/%d", id));
    }

}
