package com.smartstore.erp.inventory.entity;

import com.smartstore.erp.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_adjustments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockAdjustment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "adjustment_type", nullable = false)
    private String adjustmentType;

    @Column(nullable = false)
    private Integer quantity;

    private String reason;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}