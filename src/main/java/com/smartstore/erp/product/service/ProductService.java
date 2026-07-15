package com.smartstore.erp.product.service;

import com.smartstore.erp.product.dto.CreateProductRequest;
import com.smartstore.erp.product.dto.ProductResponse;
import com.smartstore.erp.product.dto.UpdateProductRequest;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(
            CreateProductRequest request
    );

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(
            Long id,
            UpdateProductRequest request
    );

    void deleteProduct(Long id);

    ProductResponse updateProductStatus(
            Long id,
            Boolean active
    );

    List<ProductResponse> searchProducts(String keyword);

    List<ProductResponse> getLowStockProducts();
}