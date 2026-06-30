package com.smartstore.erp.product.service;

import com.smartstore.erp.exception.DuplicateResourceException;
import com.smartstore.erp.product.dto.CreateProductRequest;
import com.smartstore.erp.product.dto.ProductResponse;
import com.smartstore.erp.product.entity.Product;
import com.smartstore.erp.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {

        if (productRepository.existsBySku(request.getSku())) {
            throw new DuplicateResourceException("Product SKU already exists");
        }

        if (request.getBarcode() != null && productRepository.existsByBarcode(request.getBarcode())) {
            throw new DuplicateResourceException("Product barcode already exists");
        }

        Product product = Product.builder()
                .name(request.getName())
                .sku(request.getSku())
                .barcode(request.getBarcode())
                .description(request.getDescription())
                .sellingPrice(request.getSellingPrice())
                .costPrice(request.getCostPrice())
                .quantity(request.getQuantity())
                .minimumStock(request.getMinimumStock())
                .status(true)
                .createdAt(LocalDateTime.now())
                .build();

        Product savedProduct = productRepository.save(product);

        return ProductResponse.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .sku(savedProduct.getSku())
                .barcode(savedProduct.getBarcode())
                .description(savedProduct.getDescription())
                .sellingPrice(savedProduct.getSellingPrice())
                .costPrice(savedProduct.getCostPrice())
                .quantity(savedProduct.getQuantity())
                .minimumStock(savedProduct.getMinimumStock())
                .status(savedProduct.getStatus())
                .build();
    }
}