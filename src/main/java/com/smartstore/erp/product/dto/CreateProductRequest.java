package com.smartstore.erp.product.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "SKU is required")
    private String sku;

    private String barcode;

    private String description;

    @NotNull(message = "Selling price is required")
    @Positive
    private BigDecimal sellingPrice;

    @NotNull(message = "Cost price is required")
    @Positive
    private BigDecimal costPrice;

    @NotNull
    @Min(0)
    private Integer quantity;

    @NotNull
    @Min(0)
    private Integer minimumStock;
    
    @NotNull(message = "Category is required")
    private Long categoryId;
}