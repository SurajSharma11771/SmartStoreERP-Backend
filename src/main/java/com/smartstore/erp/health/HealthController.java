package com.smartstore.erp.health;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartstore.erp.common.ApiResponse;

@RestController
public class HealthController {

    @GetMapping("/api/v1/health")
    public ApiResponse<Map<String, String>> healthCheck() {
        return ApiResponse.success(
                "Health check successful",
                Map.of(
                        "status", "UP",
                        "service", "SmartStore ERP Backend",
                        "version", "1.0.0"
                )
        );
    }

    
}