package com.smartstore.erp.supplier.service;

import com.smartstore.erp.exception.DuplicateResourceException;
import com.smartstore.erp.exception.ResourceNotFoundException;
import com.smartstore.erp.supplier.dto.SupplierRequest;
import com.smartstore.erp.supplier.dto.SupplierResponse;
import com.smartstore.erp.supplier.entity.Supplier;
import com.smartstore.erp.supplier.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public SupplierResponse createSupplier(SupplierRequest request) {
        if (request.getEmail() != null && supplierRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Supplier email already exists");
        }

        if (request.getGstNumber() != null && supplierRepository.existsByGstNumber(request.getGstNumber())) {
            throw new DuplicateResourceException("Supplier GST number already exists");
        }

        Supplier supplier = Supplier.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .gstNumber(request.getGstNumber())
                .status(true)
                .createdAt(LocalDateTime.now())
                .build();

        return mapToResponse(supplierRepository.save(supplier));
    }

    @Override
    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public SupplierResponse getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        return mapToResponse(supplier);
    }

    @Override
    public SupplierResponse updateSupplier(Long id, SupplierRequest request) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        supplier.setName(request.getName());
        supplier.setEmail(request.getEmail());
        supplier.setPhone(request.getPhone());
        supplier.setAddress(request.getAddress());
        supplier.setGstNumber(request.getGstNumber());
        supplier.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(supplierRepository.save(supplier));
    }

    @Override
    public void deleteSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        supplierRepository.delete(supplier);
    }

    private SupplierResponse mapToResponse(Supplier supplier) {
        return SupplierResponse.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .email(supplier.getEmail())
                .phone(supplier.getPhone())
                .address(supplier.getAddress())
                .gstNumber(supplier.getGstNumber())
                .status(supplier.getStatus())
                .build();
    }
}