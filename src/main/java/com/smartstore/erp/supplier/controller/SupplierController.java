package com.smartstore.erp.supplier.controller;

import com.smartstore.erp.common.ApiResponse;
import com.smartstore.erp.supplier.dto.SupplierRequest;
import com.smartstore.erp.supplier.dto.SupplierResponse;
import com.smartstore.erp.supplier.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public ApiResponse<SupplierResponse> createSupplier(@Valid @RequestBody SupplierRequest request) {
        return ApiResponse.success("Supplier created successfully", supplierService.createSupplier(request));
    }

    @GetMapping
    public ApiResponse<List<SupplierResponse>> getAllSuppliers() {
        return ApiResponse.success("Suppliers fetched successfully", supplierService.getAllSuppliers());
    }

    @GetMapping("/{id}")
    public ApiResponse<SupplierResponse> getSupplierById(@PathVariable Long id) {
        return ApiResponse.success("Supplier fetched successfully", supplierService.getSupplierById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<SupplierResponse> updateSupplier(
            @PathVariable Long id,
            @Valid @RequestBody SupplierRequest request) {
        return ApiResponse.success("Supplier updated successfully", supplierService.updateSupplier(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Object> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ApiResponse.success("Supplier deleted successfully", null);
    }
}