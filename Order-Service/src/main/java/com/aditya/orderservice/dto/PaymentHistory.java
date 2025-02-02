package com.aditya.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentHistory {

    private Long id;

    private Long orderId;

    private String paymentId;

    private Long customerId;

    private Long productId;

    private Integer productQuantity;

    private Double totalAmount;

    private String paymentStatus;

}
