package com.smartstore.erp.sale.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleDetailsResponse {

    private Long id;

    private String customerName;

    private String invoiceNumber;

    private BigDecimal totalAmount;

    private String status;

    private LocalDateTime saleDate;

    private List<SaleItemResponse> items;
}