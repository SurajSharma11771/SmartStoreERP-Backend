package com.smartstore.erp.purchase.repository;

import com.smartstore.erp.purchase.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {

    List<PurchaseItem> findByPurchaseId(Long purchaseId);
    void deleteByPurchaseId(Long purchaseId);
}