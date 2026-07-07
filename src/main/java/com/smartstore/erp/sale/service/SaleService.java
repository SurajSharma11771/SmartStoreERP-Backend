package com.smartstore.erp.sale.service;

import com.smartstore.erp.sale.dto.SaleDetailsResponse;
import com.smartstore.erp.sale.dto.SaleRequest;
import com.smartstore.erp.sale.dto.SaleResponse;
import com.smartstore.erp.sale.dto.SalesReturnRequest;
import java.util.List;

public interface SaleService {

    String createSale(SaleRequest request);
    String returnSale(SalesReturnRequest request);
    List<SaleResponse> getAllSales();

    SaleDetailsResponse getSaleById(Long id);
    void deleteSale(Long id);
}