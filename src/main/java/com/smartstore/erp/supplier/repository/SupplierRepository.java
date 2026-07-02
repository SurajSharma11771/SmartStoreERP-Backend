package com.smartstore.erp.supplier.repository;

import com.smartstore.erp.supplier.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    boolean existsByEmail(String email);

    boolean existsByGstNumber(String gstNumber);
}