package com.smartstore.erp.purchase.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseReturnRequest {

    private Long purchaseId;

    private Long productId;

    private Integer quantity;

    private String reason;
}