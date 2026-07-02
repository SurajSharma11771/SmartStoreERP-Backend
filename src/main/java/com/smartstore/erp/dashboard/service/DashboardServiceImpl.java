package com.smartstore.erp.dashboard.service;

import com.smartstore.erp.category.repository.CategoryRepository;
import com.smartstore.erp.customer.repository.CustomerRepository;
import com.smartstore.erp.dashboard.dto.DashboardSummaryResponse;
import com.smartstore.erp.product.repository.ProductRepository;
import com.smartstore.erp.supplier.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final CustomerRepository customerRepository;

    @Override
    public DashboardSummaryResponse getSummary() {
        long totalProducts = productRepository.count();
        long totalCategories = categoryRepository.count();
        long totalSuppliers = supplierRepository.count();
        long totalCustomers = customerRepository.count();

        long lowStockProducts = productRepository.findAll()
                .stream()
                .filter(product -> product.getQuantity() <= product.getMinimumStock())
                .count();

        return DashboardSummaryResponse.builder()
                .totalProducts(totalProducts)
                .totalCategories(totalCategories)
                .totalSuppliers(totalSuppliers)
                .totalCustomers(totalCustomers)
                .lowStockProducts(lowStockProducts)
                .build();
    }
}