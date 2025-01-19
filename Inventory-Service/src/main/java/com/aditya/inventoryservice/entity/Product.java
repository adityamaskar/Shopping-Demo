package com.aditya.inventoryservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private Long id;

    private String name;

    private String description;

    private Double price;

    private Integer quantityInStock;

}
