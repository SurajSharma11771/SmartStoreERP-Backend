package com.smartstore.erp.purchase.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseItemRequest {

    private Long productId;

    private Integer quantity;

    private BigDecimal purchasePrice;
}