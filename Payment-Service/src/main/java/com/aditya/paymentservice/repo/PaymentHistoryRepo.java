package com.aditya.paymentservice.repo;

import com.aditya.paymentservice.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentHistoryRepo extends JpaRepository<PaymentHistory, Long> {
}
