package com.smartstore.erp.purchase.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PurchaseResponse {

    private Long id;
    private String supplierName;
    private String invoiceNumber;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime purchaseDate;
}