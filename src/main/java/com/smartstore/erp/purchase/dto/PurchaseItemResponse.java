package com.smartstore.erp.purchase.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PurchaseItemResponse {

    private String productName;
    private Integer quantity;
    private BigDecimal purchasePrice;
    private BigDecimal totalPrice;
}