package org.example.spacecatsmarket.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spacecatsmarket.domain.Product;
import org.example.spacecatsmarket.repository.ProductRepository;
import org.example.spacecatsmarket.repository.entity.ProductEntity;
import org.example.spacecatsmarket.service.ProductService;
import org.example.spacecatsmarket.service.exception.PersistenceException;
import org.example.spacecatsmarket.service.exception.ProductNotFoundException;
import org.example.spacecatsmarket.service.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productMapper.toProductList((List<ProductEntity>) productRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(Long productId) {
        var productEntity = productRepository.findById(productId).orElseThrow(() -> {
            log.info("Product with id {} not found", productId);
            return new ProductNotFoundException(productId);
        });

        return productMapper.toProduct(productEntity);
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public Product createProduct(Product product) {
        try {
            var savedProduct = productRepository.save(productMapper.toProductEntity(product));
            return productMapper.toProduct(savedProduct);
        } catch (Exception ex) {
            log.error("Exception occurred while saving customer details");
            throw new PersistenceException(ex);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public Product updateProduct(Long id, Product updatedProduct) {
        var existingProduct =  productMapper.toProduct(productRepository.findById(id).orElseThrow(() -> {
            log.info("Product with id {} not found", id);
            return new ProductNotFoundException(id);
        }));

        productMapper.updateProduct(updatedProduct, existingProduct);
        var savedProductEntity = productRepository.save(productMapper.toProductEntity(existingProduct));

        return productMapper.toProduct(savedProductEntity);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        try {
            productRepository.deleteById(productId);
        } catch (Exception ex) {
            log.error("Exception occurred while deleting customer details with id {}", productId);
            throw new PersistenceException(ex);
        }
    }
}
