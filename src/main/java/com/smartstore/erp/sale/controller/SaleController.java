package com.smartstore.erp.sale.controller;

import com.smartstore.erp.common.ApiResponse;
import com.smartstore.erp.sale.dto.SaleDetailsResponse;
import com.smartstore.erp.sale.dto.SaleRequest;
import com.smartstore.erp.sale.dto.SaleResponse;
import com.smartstore.erp.sale.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.smartstore.erp.sale.dto.SalesReturnRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public ApiResponse<String> createSale(@RequestBody SaleRequest request) {
        return ApiResponse.success(
                "Sale created successfully",
                saleService.createSale(request)
        );
    }

    @GetMapping
    public ApiResponse<List<SaleResponse>> getAllSales() {
        return ApiResponse.success(
                "Sales fetched successfully",
                saleService.getAllSales()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<SaleDetailsResponse> getSaleById(@PathVariable Long id) {
        return ApiResponse.success(
                "Sale details fetched successfully",
                saleService.getSaleById(id)
        );
    }
    @DeleteMapping("/{id}")
public ApiResponse<String> deleteSale(@PathVariable Long id) {

    saleService.deleteSale(id);

    return ApiResponse.success(
            "Sale deleted successfully",
            "Sale deleted successfully"
    );
}
@PostMapping("/return")
public ApiResponse<String> returnSale(@RequestBody SalesReturnRequest request) {
    return ApiResponse.success(
            "Sales return completed successfully",
            saleService.returnSale(request)
    );
}
}