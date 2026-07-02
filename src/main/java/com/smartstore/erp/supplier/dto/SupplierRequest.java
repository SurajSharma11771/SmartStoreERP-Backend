package com.smartstore.erp.supplier.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierRequest {

    @NotBlank(message = "Supplier name is required")
    private String name;

    @Email(message = "Please enter a valid email")
    private String email;

    private String phone;
    private String address;
    private String gstNumber;
}