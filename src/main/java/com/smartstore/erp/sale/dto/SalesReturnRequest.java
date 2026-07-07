package com.smartstore.erp.sale.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesReturnRequest {

    private Long saleId;

    private Long productId;

    private Integer quantity;

    private String reason;
}