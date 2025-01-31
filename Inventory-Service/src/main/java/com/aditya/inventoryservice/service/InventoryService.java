package com.aditya.inventoryservice.service;

import com.aditya.inventoryservice.dto.OrderDTO;
import com.aditya.inventoryservice.dto.PaymentHistory;
import com.aditya.inventoryservice.dto.Status;
import com.aditya.inventoryservice.entity.Product;
import com.aditya.inventoryservice.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final ProductRepo productRepo;

    private final KafkaTemplate<String, OrderDTO> kafkaTemplateInventory;


    public void addProducts(List<Product> products) {
        productRepo.saveAll(products);
    }

    @KafkaListener(topics = "OrderCreated", groupId = "inventory-group", containerFactory = "kafkaListenerContainerFactoryOrder")
    public void createOrderEvent(OrderDTO order) {
        Product product = productRepo.findById(order.getProductId()).orElse(null);
        if(product == null || product.getQuantityInStock() == 0 || product.getQuantityInStock() < order.getQuantity()){
            sendFailureEventToOrderService(order);
        }
        else {
            product.setQuantityInStock(product.getQuantityInStock() - order.getQuantity());
            productRepo.save(product);
            try {
                sendOrderEventToPaymentService(order);
            }
            catch (Exception e){
                log.error("Error sending order event to payment service : {}", e.getMessage());
            }
        }
    }

    public void sendOrderEventToPaymentService(OrderDTO order) {
        log.info("Sending order event to payment service : {}", order);
        CompletableFuture<SendResult<String, OrderDTO>> send = kafkaTemplateInventory.send("InventoryReserved", order);
        log.info("Order event sent to payment service");
    }

    public void sendFailureEventToOrderService(OrderDTO order) {
        log.info("Sending failure event to order service as the ordered products are either not available or out of stock  : {}", order);
        order.setStatus(Status.InventoryNotFound);
        CompletableFuture<SendResult<String, OrderDTO>> send = kafkaTemplateInventory.send("InventoryFailed", order);
        log.info("Failure event sent to order service");
    }

    public Product getProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    @KafkaListener(topics = "payment-service-failure", groupId = "inventory-group", containerFactory = "kafkaListenerContainerFactoryPayment")
    public void createPaymentFailureEvent(PaymentHistory paymentHistory) {
        log.info("Payment failed for order id: {}", paymentHistory.getOrderId());
        Product product = productRepo.findById(paymentHistory.getProductId()).orElse(null);
        if(product != null){
            product.setQuantityInStock(product.getQuantityInStock() + paymentHistory.getProductQuantity());
            productRepo.save(product);
            sendFailedPaymentEventToOrderService(paymentHistory);
            log.info("Stock added back for product id: {}", product.getId());
        }
        else {
            log.error("Product not found for order id: {}", paymentHistory.getOrderId());
        }
    }

    private void sendFailedPaymentEventToOrderService(PaymentHistory paymentHistory) {
        log.info("Sending failed payment event to order service : {}", paymentHistory);
        OrderDTO order = OrderDTO.builder().id(paymentHistory.getOrderId())
                .status(Status.PaymentFailed)
                .customerId(paymentHistory.getCustomerId())
                .productId(paymentHistory.getProductId())
                .quantity(paymentHistory.getProductQuantity())
                .build();
        CompletableFuture<SendResult<String, OrderDTO>> send = kafkaTemplateInventory.send("InventoryFailed", order);
        log.info("Failed payment event sent to order service");
    }
}
