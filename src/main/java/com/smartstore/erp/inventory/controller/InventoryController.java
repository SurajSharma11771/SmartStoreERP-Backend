package com.smartstore.erp.inventory.controller;

import com.smartstore.erp.common.ApiResponse;
import com.smartstore.erp.inventory.dto.StockMovementResponse;
import com.smartstore.erp.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.smartstore.erp.inventory.dto.StockAdjustmentRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/movements/{productId}")
    public ApiResponse<List<StockMovementResponse>> getStockMovements(
            @PathVariable Long productId) {

        return ApiResponse.success(
                "Stock movements fetched successfully",
                inventoryService.getStockMovements(productId)
        );
    }
    @PostMapping("/adjust")
public ApiResponse<String> adjustStock(@RequestBody StockAdjustmentRequest request) {

    return ApiResponse.success(
            "Stock adjusted successfully",
            inventoryService.adjustStock(request)
    );
}
}