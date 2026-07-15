package com.smartstore.erp.inventory.repository;

import com.smartstore.erp.inventory.entity.StockAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockAdjustmentRepository
        extends JpaRepository<StockAdjustment, Long> {

    List<StockAdjustment> findByProductId(Long productId);

    boolean existsByProductId(Long productId);
}