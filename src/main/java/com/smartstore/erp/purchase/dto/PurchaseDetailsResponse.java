package com.smartstore.erp.purchase.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PurchaseDetailsResponse {

    private Long id;
    private String supplierName;
    private String invoiceNumber;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime purchaseDate;
    private List<PurchaseItemResponse> items;
}