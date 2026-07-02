package com.smartstore.erp.dashboard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardSummaryResponse {

    private long totalProducts;
    private long totalCategories;
    private long totalSuppliers;
    private long totalCustomers;
    private long lowStockProducts;
}