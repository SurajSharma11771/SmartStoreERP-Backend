package com.smartstore.erp.product.service;

import com.smartstore.erp.product.dto.CreateProductRequest;
import com.smartstore.erp.product.dto.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest request);
}