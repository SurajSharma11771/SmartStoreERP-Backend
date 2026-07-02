package com.smartstore.erp.purchase.dto;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseRequest {

    private Long supplierId;

    private String invoiceNumber;

    private List<PurchaseItemRequest> items;
}