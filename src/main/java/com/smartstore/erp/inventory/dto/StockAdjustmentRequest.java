package com.smartstore.erp.inventory.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockAdjustmentRequest {

    private Long productId;

    private String adjustmentType;

    private Integer quantity;

    private String reason;
}