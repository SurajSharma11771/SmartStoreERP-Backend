package com.smartstore.erp.sale.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleItemRequest {

    private Long productId;

    private Integer quantity;

    private BigDecimal sellingPrice;
}