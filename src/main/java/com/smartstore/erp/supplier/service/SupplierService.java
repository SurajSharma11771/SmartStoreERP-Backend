package com.smartstore.erp.supplier.service;

import com.smartstore.erp.supplier.dto.SupplierRequest;
import com.smartstore.erp.supplier.dto.SupplierResponse;

import java.util.List;

public interface SupplierService {

    SupplierResponse createSupplier(SupplierRequest request);

    List<SupplierResponse> getAllSuppliers();

    SupplierResponse getSupplierById(Long id);

    SupplierResponse updateSupplier(Long id, SupplierRequest request);

    void deleteSupplier(Long id);
}