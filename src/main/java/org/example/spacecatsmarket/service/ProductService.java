package org.example.spacecatsmarket.service;

import org.example.spacecatsmarket.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(Long productId);
}
