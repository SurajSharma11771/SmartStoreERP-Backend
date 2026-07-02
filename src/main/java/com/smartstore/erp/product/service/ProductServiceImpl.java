package com.smartstore.erp.product.service;

import com.smartstore.erp.exception.DuplicateResourceException;
import com.smartstore.erp.exception.ResourceNotFoundException;
import com.smartstore.erp.product.dto.CreateProductRequest;
import com.smartstore.erp.product.dto.ProductResponse;
import com.smartstore.erp.product.dto.UpdateProductRequest;
import com.smartstore.erp.product.entity.Product;
import com.smartstore.erp.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;


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
    @Override
public List<ProductResponse> getAllProducts() {

    return productRepository.findAll()
            .stream()
            .map(product -> ProductResponse.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .sku(product.getSku())
                    .barcode(product.getBarcode())
                    .description(product.getDescription())
                    .sellingPrice(product.getSellingPrice())
                    .costPrice(product.getCostPrice())
                    .quantity(product.getQuantity())
                    .minimumStock(product.getMinimumStock())
                    .status(product.getStatus())
                    .build())
            .toList();
}
@Override
public ProductResponse getProductById(Long id) {

    Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .sku(product.getSku())
            .barcode(product.getBarcode())
            .description(product.getDescription())
            .sellingPrice(product.getSellingPrice())
            .costPrice(product.getCostPrice())
            .quantity(product.getQuantity())
            .minimumStock(product.getMinimumStock())
            .status(product.getStatus())
            .build();
}
@Override
public ProductResponse updateProduct(Long id, UpdateProductRequest request) {

    Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    product.setName(request.getName());
    product.setSku(request.getSku());
    product.setBarcode(request.getBarcode());
    product.setDescription(request.getDescription());
    product.setSellingPrice(request.getSellingPrice());
    product.setCostPrice(request.getCostPrice());
    product.setQuantity(request.getQuantity());
    product.setMinimumStock(request.getMinimumStock());
    product.setStatus(request.getStatus());
    product.setUpdatedAt(LocalDateTime.now());

    Product updatedProduct = productRepository.save(product);

    return ProductResponse.builder()
            .id(updatedProduct.getId())
            .name(updatedProduct.getName())
            .sku(updatedProduct.getSku())
            .barcode(updatedProduct.getBarcode())
            .description(updatedProduct.getDescription())
            .sellingPrice(updatedProduct.getSellingPrice())
            .costPrice(updatedProduct.getCostPrice())
            .quantity(updatedProduct.getQuantity())
            .minimumStock(updatedProduct.getMinimumStock())
            .status(updatedProduct.getStatus())
            .build();
}
@Override
public void deleteProduct(Long id) {

    Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    productRepository.delete(product);
}
private ProductResponse mapToResponse(Product product) {
    return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .sku(product.getSku())
            .barcode(product.getBarcode())
            .description(product.getDescription())
            .sellingPrice(product.getSellingPrice())
            .costPrice(product.getCostPrice())
            .quantity(product.getQuantity())
            .minimumStock(product.getMinimumStock())
            .status(product.getStatus())
            .build();
}
@Override
public List<ProductResponse> searchProducts(String keyword) {
    return productRepository.findByNameContainingIgnoreCase(keyword)
            .stream()
            .map(this::mapToResponse)
            .toList();
}

@Override
public List<ProductResponse> getLowStockProducts() {
    return productRepository.findAll()
            .stream()
            .filter(product -> product.getQuantity() <= product.getMinimumStock())
            .map(this::mapToResponse)
            .toList();
}


}