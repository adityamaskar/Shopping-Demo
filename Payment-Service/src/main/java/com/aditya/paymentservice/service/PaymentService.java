package com.aditya.paymentservice.service;

import com.aditya.paymentservice.dto.OrderDTO;
import com.aditya.paymentservice.dto.ProductDTO;
import com.aditya.paymentservice.entity.Customer;
import com.aditya.paymentservice.repo.CustomerRepo;
import com.aditya.paymentservice.repo.PaymentHistoryRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    public final CustomerRepo customerRepo;

    public final PaymentHistoryRepo paymentHistoryRepo;

    public final RestTemplate restTemplate;

    @KafkaListener(topics = "inventory-service-event", groupId = "payment-group", containerFactory = "kafkaListenerContainerFactoryPayment")
    public void consumePaymentEvent(OrderDTO orderDTO) {
        if (orderDTO == null) {
            log.error("OrderDTO is null");
            return;
        } else if (orderDTO.getCustomerId() == null) {
            log.error("Customer Id is null");
            return;
        }else {
            Double totalAmount = CalculateTotalAmount(orderDTO);
            DeductAmountFromCustomerAccount(totalAmount, orderDTO.getCustomerId());
            //todo send success event
        }

    }

    public Double CalculateTotalAmount(OrderDTO orderDTO) {
        ProductDTO product = restTemplate.getForEntity("http://localhost:8080/inventory/product/" + orderDTO.getProductId(), ProductDTO.class).getBody();
        if (product != null && product.getPrice() != null) {
            double totalCost = product.getPrice() * orderDTO.getQuantity();
            log.info("Total cost of the order id " + orderDTO.getId() + " is: " + totalCost);
            return totalCost;
        }
        return null;
    }

    public void DeductAmountFromCustomerAccount(Double totalAmount, Long customerId) {
        Optional<Customer> customer = customerRepo.findById(customerId);
        if(customer.isEmpty()){
            log.error("Customer not found");
            //todo send failure event
            return;
        }
            Customer customerObj = customer.get();
            Double currentBalance = customerObj.getBalanceAmount();

        if (currentBalance >= totalAmount) {
            customerObj.setBalanceAmount(currentBalance - totalAmount);
            log.info("Amount deducted from customer account: " + totalAmount);
        } else {
            log.error("Insufficient balance in customer account");
            //todo send failure event
        }
    }
}
