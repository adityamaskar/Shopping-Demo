package com.aditya.paymentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentHistory {

    @Id
    private Long id;

    private Long orderId;

    private Long paymentId;

    private Long customerId;

    private Double totalAmount;

    private String paymentStatus;

}
