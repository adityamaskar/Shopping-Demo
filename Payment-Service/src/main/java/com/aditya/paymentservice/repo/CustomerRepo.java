package com.aditya.paymentservice.repo;

import com.aditya.paymentservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
}
