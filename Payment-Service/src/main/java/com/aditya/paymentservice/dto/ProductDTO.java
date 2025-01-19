package com.aditya.paymentservice.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @Id
    private Long id;

    private String name;

    private String description;

    private Double price;

    private Integer quantityInStock;

}
