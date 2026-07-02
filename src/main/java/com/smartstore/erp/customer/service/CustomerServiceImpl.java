package com.smartstore.erp.customer.service;

import com.smartstore.erp.customer.dto.CustomerRequest;
import com.smartstore.erp.customer.dto.CustomerResponse;
import com.smartstore.erp.customer.entity.Customer;
import com.smartstore.erp.customer.repository.CustomerRepository;
import com.smartstore.erp.exception.DuplicateResourceException;
import com.smartstore.erp.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        if (request.getEmail() != null && customerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Customer email already exists");
        }

        Customer customer = Customer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .loyaltyPoints(0)
                .status(true)
                .createdAt(LocalDateTime.now())
                .build();

        return mapToResponse(customerRepository.save(customer));
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return mapToResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        customer.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(customerRepository.save(customer));
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        customerRepository.delete(customer);
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .loyaltyPoints(customer.getLoyaltyPoints())
                .status(customer.getStatus())
                .build();
    }
}