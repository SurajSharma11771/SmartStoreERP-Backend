package com.smartstore.erp.sale.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleResponse {

    private Long id;

    private String customerName;

    private String invoiceNumber;

    private BigDecimal totalAmount;

    private String status;

    private LocalDateTime saleDate;
    private BigDecimal returnedAmount;
private BigDecimal netAmount;
private String returnStatus;
}