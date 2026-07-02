package com.smartstore.erp.dashboard.controller;

import com.smartstore.erp.common.ApiResponse;
import com.smartstore.erp.dashboard.dto.DashboardSummaryResponse;
import com.smartstore.erp.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ApiResponse<DashboardSummaryResponse> getSummary() {
        return ApiResponse.success(
                "Dashboard summary fetched successfully",
                dashboardService.getSummary()
        );
    }
}