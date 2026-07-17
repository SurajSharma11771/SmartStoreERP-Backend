package com.smartstore.erp.product.service;

import com.smartstore.erp.category.entity.Category;
import com.smartstore.erp.category.repository.CategoryRepository;
import com.smartstore.erp.exception.DuplicateResourceException;
import com.smartstore.erp.exception.ProductInUseException;
import com.smartstore.erp.exception.ResourceNotFoundException;
import com.smartstore.erp.inventory.repository.StockAdjustmentRepository;
import com.smartstore.erp.product.dto.CreateProductRequest;
import com.smartstore.erp.product.dto.ProductResponse;
import com.smartstore.erp.product.dto.UpdateProductRequest;
import com.smartstore.erp.product.entity.Product;
import com.smartstore.erp.product.repository.ProductRepository;
import com.smartstore.erp.purchase.repository.PurchaseItemRepository;
import com.smartstore.erp.sale.repository.SaleItemRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SaleItemRepository saleItemRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final StockAdjustmentRepository stockAdjustmentRepository;

    @Override
    @Transactional
    public ProductResponse createProduct(
            CreateProductRequest request
    ) {
        if (productRepository.existsBySku(request.getSku())) {
            throw new DuplicateResourceException(
                    "Product SKU already exists"
            );
        }

        if (
                request.getBarcode() != null &&
                !request.getBarcode().isBlank() &&
                productRepository.existsByBarcode(
                        request.getBarcode()
                )
        ) {
            throw new DuplicateResourceException(
                    "Product barcode already exists"
            );
        }

        Category category =
                findCategoryById(request.getCategoryId());

        Product product = Product.builder()
                .name(request.getName())
                .sku(request.getSku())
                .barcode(request.getBarcode())
                .description(request.getDescription())
                .sellingPrice(request.getSellingPrice())
                .costPrice(request.getCostPrice())
                .quantity(request.getQuantity())
                .minimumStock(request.getMinimumStock())
                .category(category)
                .status(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Product savedProduct =
                productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = findProductById(id);

        return mapToResponse(product);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(
            Long id,
            UpdateProductRequest request
    ) {
        Product product = findProductById(id);

        Category category =
                findCategoryById(request.getCategoryId());

        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setBarcode(request.getBarcode());
        product.setDescription(request.getDescription());
        product.setSellingPrice(request.getSellingPrice());
        product.setCostPrice(request.getCostPrice());
        product.setQuantity(request.getQuantity());
        product.setMinimumStock(request.getMinimumStock());
        product.setCategory(category);

        if (request.getStatus() != null) {
            product.setStatus(request.getStatus());
        }

        product.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct =
                productRepository.save(product);

        return mapToResponse(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = findProductById(id);

        boolean usedInSales =
                saleItemRepository.existsByProductId(id);

        boolean usedInPurchases =
                purchaseItemRepository.existsByProductId(id);

        boolean usedInAdjustments =
                stockAdjustmentRepository.existsByProductId(id);

        if (usedInSales) {
            throw new ProductInUseException(
                    "Product is used in sales history. " +
                    "Deactivate it instead."
            );
        }

        if (usedInPurchases) {
            throw new ProductInUseException(
                    "Product is used in purchase history. " +
                    "Deactivate it instead."
            );
        }

        if (usedInAdjustments) {
            throw new ProductInUseException(
                    "Product is used in stock adjustment history. " +
                    "Deactivate it instead."
            );
        }

        productRepository.delete(product);
        productRepository.flush();
    }

    @Override
    @Transactional
    public ProductResponse updateProductStatus(
            Long id,
            Boolean active
    ) {
        Product product = findProductById(id);

        product.setStatus(Boolean.TRUE.equals(active));
        product.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct =
                productRepository.save(product);

        return mapToResponse(updatedProduct);
    }

    @Override
    public List<ProductResponse> searchProducts(
            String keyword
    ) {
        return productRepository
                .findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> getLowStockProducts() {
        return productRepository.findAll()
                .stream()
                .filter(product ->
                        Boolean.TRUE.equals(
                                product.getStatus()
                        )
                )
                .filter(product ->
                        product.getQuantity() <=
                        product.getMinimumStock()
                )
                .map(this::mapToResponse)
                .toList();
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"
                        )
                );
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found"
                        )
                );
    }

    private ProductResponse mapToResponse(
            Product product
    ) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .barcode(product.getBarcode())
                .description(product.getDescription())
                .sellingPrice(product.getSellingPrice())
                .costPrice(product.getCostPrice())
                .quantity(product.getQuantity())
                .minimumStock(product.getMinimumStock())
                .categoryId(
                        product.getCategory() != null
                                ? product.getCategory().getId()
                                : null
                )
                .categoryName(
                        product.getCategory() != null
                                ? product.getCategory().getName()
                                : null
                )
                .status(product.getStatus())
                .build();
    }
}