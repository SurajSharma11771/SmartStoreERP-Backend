package com.smartstore.erp.customer.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Integer loyaltyPoints;
    private Boolean status;
}