package com.smartstore.erp.customer.controller;

import com.smartstore.erp.common.ApiResponse;
import com.smartstore.erp.customer.dto.CustomerRequest;
import com.smartstore.erp.customer.dto.CustomerResponse;
import com.smartstore.erp.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ApiResponse<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        return ApiResponse.success("Customer created successfully", customerService.createCustomer(request));
    }

    @GetMapping
    public ApiResponse<List<CustomerResponse>> getAllCustomers() {
        return ApiResponse.success("Customers fetched successfully", customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ApiResponse<CustomerResponse> getCustomerById(@PathVariable Long id) {
        return ApiResponse.success("Customer fetched successfully", customerService.getCustomerById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<CustomerResponse> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest request) {
        return ApiResponse.success("Customer updated successfully", customerService.updateCustomer(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Object> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ApiResponse.success("Customer deleted successfully", null);
    }
}