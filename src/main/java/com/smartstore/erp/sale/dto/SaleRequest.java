package com.smartstore.erp.sale.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleRequest {

    private Long customerId;

    private String invoiceNumber;

    private List<SaleItemRequest> items;
}