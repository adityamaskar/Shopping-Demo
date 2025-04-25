package com.aditya.orderservice.repo;

import com.aditya.orderservice.entity.Order;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Observed
public interface OrderRepo extends JpaRepository<Order, Long> {
}
