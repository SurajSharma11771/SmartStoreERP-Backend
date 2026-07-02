package com.smartstore.erp.purchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartstore.erp.purchase.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}