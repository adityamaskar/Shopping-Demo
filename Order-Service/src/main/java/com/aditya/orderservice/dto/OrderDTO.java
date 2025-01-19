package com.aditya.orderservice.dto;


import com.aditya.orderservice.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Long id;

    private Long productId;

    private Integer quantity;

    private Long customerId;

    private Status status;
}
