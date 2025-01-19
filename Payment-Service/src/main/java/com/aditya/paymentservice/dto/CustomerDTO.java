package com.aditya.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {

    private String name;

    private String email;

    private String phone;

    private String address;

    private String city;

    private Double balanceAmount;
}
