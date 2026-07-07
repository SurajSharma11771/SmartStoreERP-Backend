package com.smartstore.erp.inventory.service;

import com.smartstore.erp.inventory.dto.StockMovementResponse;

import java.util.List;

import com.smartstore.erp.inventory.dto.StockAdjustmentRequest;

public interface InventoryService {

    List<StockMovementResponse> getStockMovements(Long productId);
    String adjustStock(StockAdjustmentRequest request);
}