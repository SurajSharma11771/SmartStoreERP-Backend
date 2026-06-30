package com.smartstore.erp.product.controller;

import com.smartstore.erp.common.ApiResponse;
import com.smartstore.erp.product.dto.CreateProductRequest;
import com.smartstore.erp.product.dto.ProductResponse;
import com.smartstore.erp.product.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ApiResponse.success("Product created successfully", response);
    }
}