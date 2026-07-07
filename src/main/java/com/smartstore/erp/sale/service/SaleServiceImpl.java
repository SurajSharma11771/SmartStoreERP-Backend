package com.smartstore.erp.sale.service;

import com.smartstore.erp.customer.entity.Customer;
import com.smartstore.erp.customer.repository.CustomerRepository;
import com.smartstore.erp.exception.ResourceNotFoundException;
import com.smartstore.erp.product.entity.Product;
import com.smartstore.erp.product.repository.ProductRepository;
import com.smartstore.erp.sale.dto.*;
import com.smartstore.erp.sale.entity.Sale;
import com.smartstore.erp.sale.entity.SaleItem;
import com.smartstore.erp.sale.repository.SaleItemRepository;
import com.smartstore.erp.sale.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.smartstore.erp.sale.entity.SalesReturn;
import com.smartstore.erp.sale.repository.SalesReturnRepository;
@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final SalesReturnRepository salesReturnRepository;

    @Override
    public String createSale(SaleRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        BigDecimal totalAmount = BigDecimal.ZERO;

        Sale sale = Sale.builder()
                .customer(customer)
                .invoiceNumber(request.getInvoiceNumber())
                .saleDate(LocalDateTime.now())
                .totalAmount(BigDecimal.ZERO)
                .status("COMPLETED")
                .createdAt(LocalDateTime.now())
                .build();

        Sale savedSale = saleRepository.save(sale);

        for (SaleItemRequest itemRequest : request.getItems()) {

            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            if (product.getQuantity() < itemRequest.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }

            BigDecimal itemTotal = itemRequest.getSellingPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            SaleItem saleItem = SaleItem.builder()
                    .sale(savedSale)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .sellingPrice(itemRequest.getSellingPrice())
                    .totalPrice(itemTotal)
                    .build();

            saleItemRepository.save(saleItem);

            product.setQuantity(product.getQuantity() - itemRequest.getQuantity());
            productRepository.save(product);

            totalAmount = totalAmount.add(itemTotal);
        }

        savedSale.setTotalAmount(totalAmount);
        saleRepository.save(savedSale);

        return "Sale created successfully";
    }
private SaleResponse mapSaleToResponse(Sale sale) {
    List<SalesReturn> returns = salesReturnRepository.findBySaleId(sale.getId());

    BigDecimal returnedAmount = returns.stream()
            .map(returnItem -> {
                List<SaleItem> saleItems = saleItemRepository.findBySaleId(sale.getId());

                return saleItems.stream()
                        .filter(item -> item.getProduct().getId().equals(returnItem.getProduct().getId()))
                        .findFirst()
                        .map(item -> item.getSellingPrice()
                                .multiply(BigDecimal.valueOf(returnItem.getQuantity())))
                        .orElse(BigDecimal.ZERO);
            })
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal netAmount = sale.getTotalAmount().subtract(returnedAmount);

    String returnStatus = "NONE";

    if (returnedAmount.compareTo(BigDecimal.ZERO) > 0 &&
            returnedAmount.compareTo(sale.getTotalAmount()) < 0) {
        returnStatus = "PARTIAL_RETURN";
    }

    if (returnedAmount.compareTo(sale.getTotalAmount()) >= 0) {
        returnStatus = "RETURNED";
    }

    return SaleResponse.builder()
            .id(sale.getId())
            .customerName(sale.getCustomer().getName())
            .invoiceNumber(sale.getInvoiceNumber())
            .totalAmount(sale.getTotalAmount())
            .returnedAmount(returnedAmount)
            .netAmount(netAmount)
            .returnStatus(returnStatus)
            .status(sale.getStatus())
            .saleDate(sale.getSaleDate())
            .build();
}
    @Override
public List<SaleResponse> getAllSales() {
    return saleRepository.findAll()
            .stream()
            .map(this::mapSaleToResponse)
            .toList();
}

    @Override
    public SaleDetailsResponse getSaleById(Long id) {

        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

        List<SaleItemResponse> items = saleItemRepository.findBySaleId(id)
                .stream()
                .map(item -> SaleItemResponse.builder()
                .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .sellingPrice(item.getSellingPrice())
                        .totalPrice(item.getTotalPrice())
                        .build())
                        
                .toList();

        return SaleDetailsResponse.builder()
                .id(sale.getId())
                .customerName(sale.getCustomer().getName())
                .invoiceNumber(sale.getInvoiceNumber())
                .totalAmount(sale.getTotalAmount())
                .status(sale.getStatus())
                .saleDate(sale.getSaleDate())
                .items(items)
                .build();
    }
    @Override
@Transactional
public void deleteSale(Long id) {

    Sale sale = saleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

    List<SaleItem> items = saleItemRepository.findBySaleId(id);

    for (SaleItem item : items) {
        Product product = item.getProduct();

        product.setQuantity(product.getQuantity() + item.getQuantity());

        productRepository.save(product);
    }

    saleItemRepository.deleteBySaleId(id);

    saleRepository.delete(sale);
}
@Override
@Transactional
public String returnSale(SalesReturnRequest request) {

    Sale sale = saleRepository.findById(request.getSaleId())
            .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

    Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    SaleItem soldItem = saleItemRepository.findBySaleId(sale.getId())
            .stream()
            .filter(item -> item.getProduct().getId().equals(product.getId()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("This product was not sold in this sale"));

    int alreadyReturnedQty = salesReturnRepository
            .findBySaleIdAndProductId(sale.getId(), product.getId())
            .stream()
            .mapToInt(SalesReturn::getQuantity)
            .sum();

    int remainingReturnableQty = soldItem.getQuantity() - alreadyReturnedQty;

    if (request.getQuantity() <= 0) {
        throw new RuntimeException("Return quantity must be greater than zero");
    }

    if (request.getQuantity() > remainingReturnableQty) {
        throw new RuntimeException(
                "Cannot return more than sold quantity. Remaining returnable quantity: "
                        + remainingReturnableQty
        );
    }

    product.setQuantity(product.getQuantity() + request.getQuantity());
    productRepository.save(product);

    SalesReturn salesReturn = SalesReturn.builder()
            .sale(sale)
            .product(product)
            .quantity(request.getQuantity())
            .reason(request.getReason())
            .returnDate(LocalDateTime.now())
            .build();

    salesReturnRepository.save(salesReturn);

    return "Sales return completed successfully";
}
}