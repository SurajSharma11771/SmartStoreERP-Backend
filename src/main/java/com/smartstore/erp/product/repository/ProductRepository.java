package com.smartstore.erp.product.repository;

import com.smartstore.erp.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsBySku(String sku);

    boolean existsByBarcode(String barcode);
}