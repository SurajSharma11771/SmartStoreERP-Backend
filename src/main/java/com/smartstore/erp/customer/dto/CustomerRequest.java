package com.smartstore.erp.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {

    @NotBlank(message = "Customer name is required")
    private String name;

    @Email(message = "Please enter a valid email")
    private String email;

    private String phone;
    private String address;
}