package com.smartstore.erp.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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