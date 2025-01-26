package com.aditya.paymentservice.service;

import com.aditya.paymentservice.dto.OrderDTO;
import com.aditya.paymentservice.dto.ProductDTO;
import com.aditya.paymentservice.entity.Customer;
import com.aditya.paymentservice.entity.PaymentHistory;
import com.aditya.paymentservice.repo.CustomerRepo;
import com.aditya.paymentservice.repo.PaymentHistoryRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    public final CustomerRepo customerRepo;

    public final PaymentHistoryRepo paymentHistoryRepo;

    public final RestTemplate restTemplate;

    public final KafkaTemplate<String, PaymentHistory> kafkaTemplateInventory;

    @KafkaListener(topics = "inventory-service-event", groupId = "payment-group", containerFactory = "kafkaListenerContainerFactoryPayment")
    public void consumePaymentEvent(OrderDTO orderDTO) {
        if (orderDTO == null) {
            log.error("OrderDTO is null");
//            todo send failure event
            return;
        } else if (orderDTO.getCustomerId() == null) {
            log.error("Customer Id is null");
//            todo send failure event
            return;
        }else {
            Double totalAmount = CalculateTotalAmount(orderDTO);
            DeductAmountFromCustomerAccount(totalAmount, orderDTO);
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

    public void DeductAmountFromCustomerAccount(Double totalAmount, OrderDTO orderDTO) {
        Optional<Customer> customer = customerRepo.findById(orderDTO.getCustomerId());
        if(customer.isEmpty()){
            log.error("Customer not found");
            //todo send failure event
            return;
        }
            Customer customerObj = customer.get();
            Double currentBalance = customerObj.getBalanceAmount();

        if (currentBalance >= totalAmount) {
            customerObj.setBalanceAmount(currentBalance - totalAmount);
            Customer save = customerRepo.save(customerObj);
            log.info("Amount deducted from customer account, current balance: " + save.getBalanceAmount());

            PaymentHistory paymentSuccessful = PaymentHistory.builder().customerId(orderDTO.getCustomerId())
                    .paymentStatus("Payment Successful")
                    .productId(orderDTO.getProductId())
                    .productQuantity(orderDTO.getQuantity())
                    .totalAmount(totalAmount)
                    .orderId(orderDTO.getId())
                    .paymentId(UUID.randomUUID().toString())
                    .build();

            PaymentHistory saved = paymentHistoryRepo.save(paymentSuccessful);
            log.info("Amount deducted from customer account: " + totalAmount + ", Transaction Id : " + saved.getPaymentId());
        } else {
            log.error("Insufficient balance in customer account");
            PaymentHistory paymentFailed = PaymentHistory.builder().customerId(orderDTO.getCustomerId())
                    .paymentStatus("Payment Failed")
                    .productId(orderDTO.getProductId())
                    .productQuantity(orderDTO.getQuantity())
                    .totalAmount(totalAmount)
                    .orderId(orderDTO.getId())
                    .paymentId(UUID.randomUUID().toString())
                    .build();
            PaymentHistory saved = paymentHistoryRepo.save(paymentFailed);
            //todo send failure event
            CompletableFuture<SendResult<String, PaymentHistory>> send = kafkaTemplateInventory.send("payment-service-failure", paymentFailed);
            send.thenAccept(result -> log.info("Payment failed event sent to Kafka topic, Transaction Id : " + saved.getPaymentId()));


        }
    }

}
