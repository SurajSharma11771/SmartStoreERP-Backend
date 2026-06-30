package com.smartstore.erp.product.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long id;

    private String name;

    private String sku;

    private String barcode;

    private String description;

    private BigDecimal sellingPrice;

    private BigDecimal costPrice;

    private Integer quantity;

    private Integer minimumStock;

    private Boolean status;
}