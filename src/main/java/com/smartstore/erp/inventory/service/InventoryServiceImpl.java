package com.smartstore.erp.inventory.service;

import com.smartstore.erp.exception.ResourceNotFoundException;
import com.smartstore.erp.inventory.dto.StockAdjustmentRequest;
import com.smartstore.erp.inventory.dto.StockMovementResponse;
import com.smartstore.erp.inventory.entity.StockAdjustment;
import com.smartstore.erp.inventory.repository.StockAdjustmentRepository;
import com.smartstore.erp.product.entity.Product;
import com.smartstore.erp.product.repository.ProductRepository;
import com.smartstore.erp.purchase.entity.PurchaseItem;
import com.smartstore.erp.purchase.repository.PurchaseItemRepository;
import com.smartstore.erp.sale.entity.SaleItem;
import com.smartstore.erp.sale.repository.SaleItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final PurchaseItemRepository purchaseItemRepository;
    private final SaleItemRepository saleItemRepository;
    private final ProductRepository productRepository;
    private final StockAdjustmentRepository stockAdjustmentRepository;

    @Override
    public List<StockMovementResponse> getStockMovements(Long productId) {
        List<StockMovementResponse> purchaseMovements =
                purchaseItemRepository.findByProductId(productId)
                        .stream()
                        .map(this::mapPurchaseItem)
                        .toList();

        List<StockMovementResponse> saleMovements =
                saleItemRepository.findByProductId(productId)
                        .stream()
                        .map(this::mapSaleItem)
                        .toList();

        List<StockMovementResponse> adjustmentMovements =
                stockAdjustmentRepository.findByProductId(productId)
                        .stream()
                        .map(this::mapAdjustment)
                        .toList();

        return Stream.of(
                        purchaseMovements.stream(),
                        saleMovements.stream(),
                        adjustmentMovements.stream()
                )
                .flatMap(stream -> stream)
                .sorted(Comparator.comparing(StockMovementResponse::getDate).reversed())
                .toList();
    }

    private StockMovementResponse mapPurchaseItem(PurchaseItem item) {
        return StockMovementResponse.builder()
                .type("PURCHASE")
                .referenceNumber(item.getPurchase().getInvoiceNumber())
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .price(item.getPurchasePrice())
                .totalAmount(item.getTotalPrice())
                .date(item.getPurchase().getPurchaseDate())
                .build();
    }

    private StockMovementResponse mapSaleItem(SaleItem item) {
        return StockMovementResponse.builder()
                .type("SALE")
                .referenceNumber(item.getSale().getInvoiceNumber())
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .price(item.getSellingPrice())
                .totalAmount(item.getTotalPrice())
                .date(item.getSale().getSaleDate())
                .build();
    }

    private StockMovementResponse mapAdjustment(StockAdjustment adjustment) {
        return StockMovementResponse.builder()
                .type(adjustment.getAdjustmentType())
                .referenceNumber("Stock Adjustment")
                .productName(adjustment.getProduct().getName())
                .quantity(adjustment.getQuantity())
                .price(null)
                .totalAmount(null)
                .date(adjustment.getCreatedAt())
                .build();
    }

    @Override
    @Transactional
    public String adjustStock(StockAdjustmentRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if ("ADD".equalsIgnoreCase(request.getAdjustmentType())) {
            product.setQuantity(product.getQuantity() + request.getQuantity());
        } else {
            if (product.getQuantity() < request.getQuantity()) {
                throw new RuntimeException("Insufficient stock");
            }

            product.setQuantity(product.getQuantity() - request.getQuantity());
        }

        productRepository.save(product);

        StockAdjustment adjustment = StockAdjustment.builder()
                .product(product)
                .adjustmentType(request.getAdjustmentType())
                .quantity(request.getQuantity())
                .reason(request.getReason())
                .createdAt(LocalDateTime.now())
                .build();

        stockAdjustmentRepository.save(adjustment);

        return "Stock adjusted successfully";
    }
}