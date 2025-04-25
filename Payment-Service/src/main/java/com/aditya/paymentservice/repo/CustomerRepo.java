package com.aditya.paymentservice.repo;

import com.aditya.paymentservice.entity.Customer;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Observed
@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
}
