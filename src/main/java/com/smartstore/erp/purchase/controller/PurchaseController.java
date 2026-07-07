package com.smartstore.erp.purchase.controller;

import com.smartstore.erp.common.ApiResponse;
import com.smartstore.erp.purchase.dto.PurchaseRequest;
import com.smartstore.erp.purchase.service.PurchaseService;
import com.smartstore.erp.purchase.dto.PurchaseReturnRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.smartstore.erp.purchase.dto.PurchaseResponse;
import java.util.List;
import com.smartstore.erp.purchase.dto.PurchaseDetailsResponse;

@RestController
@RequestMapping("/api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;
    @GetMapping("/test")
public ApiResponse<String> testPurchase() {
    return ApiResponse.success("Purchase test working", "OK");
}
    @PostMapping
    public ApiResponse<String> createPurchase(@RequestBody PurchaseRequest request) {
        return ApiResponse.success(
                "Purchase created successfully",
                purchaseService.createPurchase(request)
        );
    }
    @GetMapping
public ApiResponse<List<PurchaseResponse>> getAllPurchases() {
    return ApiResponse.success(
            "Purchases fetched successfully",
            purchaseService.getAllPurchases()
    );
}
@GetMapping("/{id}")
public ApiResponse<PurchaseDetailsResponse> getPurchaseById(@PathVariable Long id) {
    return ApiResponse.success(
            "Purchase details fetched successfully",
            purchaseService.getPurchaseById(id)
    );
}
@DeleteMapping("/{id}")
public ApiResponse<String> deletePurchase(@PathVariable Long id) {

    purchaseService.deletePurchase(id);

    return ApiResponse.success(
            "Purchase deleted successfully",
            "OK"
    );
}
@PostMapping("/return")
public ApiResponse<String> returnPurchase(@RequestBody PurchaseReturnRequest request) {
    return ApiResponse.success(
            "Purchase return completed successfully",
            purchaseService.returnPurchase(request)
    );
}
}