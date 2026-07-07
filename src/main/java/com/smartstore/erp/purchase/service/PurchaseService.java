package com.smartstore.erp.purchase.service;

import com.smartstore.erp.purchase.dto.PurchaseRequest;
import com.smartstore.erp.purchase.dto.PurchaseResponse;
import java.util.List;
import com.smartstore.erp.purchase.dto.PurchaseDetailsResponse;
import com.smartstore.erp.purchase.dto.PurchaseReturnRequest;
public interface PurchaseService {

    String createPurchase(PurchaseRequest request);
    String returnPurchase(PurchaseReturnRequest request);
    List<PurchaseResponse> getAllPurchases();
    PurchaseDetailsResponse getPurchaseById(Long id);
    void deletePurchase(Long id);
    
}