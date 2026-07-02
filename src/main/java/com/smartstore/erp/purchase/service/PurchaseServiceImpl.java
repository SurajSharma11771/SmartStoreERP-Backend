package com.smartstore.erp.purchase.service;

import com.smartstore.erp.exception.ResourceNotFoundException;
import com.smartstore.erp.product.entity.Product;
import com.smartstore.erp.product.repository.ProductRepository;
import com.smartstore.erp.purchase.dto.PurchaseItemRequest;
import com.smartstore.erp.purchase.dto.PurchaseRequest;
import com.smartstore.erp.purchase.entity.Purchase;
import com.smartstore.erp.purchase.entity.PurchaseItem;
import com.smartstore.erp.purchase.repository.PurchaseItemRepository;
import com.smartstore.erp.purchase.repository.PurchaseRepository;
import com.smartstore.erp.supplier.entity.Supplier;
import com.smartstore.erp.supplier.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.smartstore.erp.purchase.dto.PurchaseResponse;
import java.util.List;

import com.smartstore.erp.purchase.dto.PurchaseDetailsResponse;
import com.smartstore.erp.purchase.dto.PurchaseItemResponse;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    @Override
    public String createPurchase(PurchaseRequest request) {

        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        BigDecimal totalAmount = BigDecimal.ZERO;

        Purchase purchase = Purchase.builder()
                .supplier(supplier)
                .invoiceNumber(request.getInvoiceNumber())
                .purchaseDate(LocalDateTime.now())
                .totalAmount(BigDecimal.ZERO)
                .status("COMPLETED")
                .createdAt(LocalDateTime.now())
                .build();

        Purchase savedPurchase = purchaseRepository.save(purchase);

        for (PurchaseItemRequest itemRequest : request.getItems()) {

            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            BigDecimal itemTotal = itemRequest.getPurchasePrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            PurchaseItem purchaseItem = PurchaseItem.builder()
                    .purchase(savedPurchase)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .purchasePrice(itemRequest.getPurchasePrice())
                    .totalPrice(itemTotal)
                    .build();

            purchaseItemRepository.save(purchaseItem);

            product.setQuantity(product.getQuantity() + itemRequest.getQuantity());
            productRepository.save(product);

            totalAmount = totalAmount.add(itemTotal);
        }

        savedPurchase.setTotalAmount(totalAmount);
        purchaseRepository.save(savedPurchase);

        return "Purchase created successfully";
    }
    @Override
public PurchaseDetailsResponse getPurchaseById(Long id) {

    Purchase purchase = purchaseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Purchase not found"));

    List<PurchaseItemResponse> items = purchaseItemRepository.findByPurchaseId(id)
            .stream()
            .map(item -> PurchaseItemResponse.builder()
                    .productName(item.getProduct().getName())
                    .quantity(item.getQuantity())
                    .purchasePrice(item.getPurchasePrice())
                    .totalPrice(item.getTotalPrice())
                    .build())
            .toList();

    return PurchaseDetailsResponse.builder()
            .id(purchase.getId())
            .supplierName(purchase.getSupplier().getName())
            .invoiceNumber(purchase.getInvoiceNumber())
            .totalAmount(purchase.getTotalAmount())
            .status(purchase.getStatus())
            .purchaseDate(purchase.getPurchaseDate())
            .items(items)
            .build();
}
    
    @Override
public List<PurchaseResponse> getAllPurchases() {
    return purchaseRepository.findAll()
            .stream()
            .map(purchase -> PurchaseResponse.builder()
                    .id(purchase.getId())
                    .supplierName(purchase.getSupplier().getName())
                    .invoiceNumber(purchase.getInvoiceNumber())
                    .totalAmount(purchase.getTotalAmount())
                    .status(purchase.getStatus())
                    .purchaseDate(purchase.getPurchaseDate())
                    .build())
            .toList();
}



}