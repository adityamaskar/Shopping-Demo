package com.aditya.inventoryservice.repo;

import com.aditya.inventoryservice.entity.Product;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Observed
public interface ProductRepo extends JpaRepository<Product, Long> {
}
