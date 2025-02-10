package com.aditya.orderservice.service;

import com.aditya.orderservice.dto.OrderDTO;
import com.aditya.orderservice.dto.PaymentHistory;
import com.aditya.orderservice.entity.Order;
import com.aditya.orderservice.entity.Status;
import com.aditya.orderservice.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepo orderRepo;

    private final KafkaTemplate<String, OrderDTO> kafkaTemplateOrder;
    public Long placeOrder(OrderDTO orderDTO) {
        Order order = Order.builder()
                .productId(orderDTO.getProductId())
                .quantity(orderDTO.getQuantity())
                .customerId(orderDTO.getCustomerId())
                .status(Status.InProgress)
                .build();
        Order save = orderRepo.save(order);
        log.info("Order saved: {}", save);
        orderDTO.setId(save.getId());
        orderDTO.setStatus(Status.InProgress);
        kafkaTemplateOrder.send("OrderCreated", orderDTO);
        log.info("Order sent to Kafka topic");
        return save.getId();
    }

    @KafkaListener(topics = "InventoryFailed", groupId = "${default.consumer.group}", containerFactory = "kafkaListenerContainerFactoryOrder")
    public void consumeInventoryFailedEvent(OrderDTO orderDTO) {
        log.info("Order failed event consumed: {}", orderDTO);
        Order order = orderRepo.findById(orderDTO.getId()).orElse(null);
        if(order != null){
            order.setStatus(orderDTO.getStatus());
            orderRepo.save(order);
            log.info("Order status updated to Failed " + order.getStatus().toString() );
        }
        else {
            log.error("Order not found with id: {}", orderDTO.getId());
        }
    }

    @KafkaListener(topics = "payment-successful", groupId = "${default.consumer.group}", containerFactory = "kafkaListenerContainerFactoryPayment")
    public void consumePaymentSuccessEvent(PaymentHistory paymentHistory) {
        if (paymentHistory.getOrderId() != null)
            log.info("Received the successful payment event from the payment-service, order ID : " + paymentHistory.getOrderId());
        else {
            log.error("Order Id is not present");
        }
        Long orderId = paymentHistory.getOrderId();
        Optional<Order> byId = orderRepo.findById(orderId);
        if(byId.isPresent() && paymentHistory.getPaymentStatus().equals("Payment Successful")){
            Order order = byId.get();
            order.setStatus(Status.Ordered);
            orderRepo.save(order);
            log.info("Order has successfully placed and updated in DB");
        }
        else {
            log.error("Order does not present in the database");
            log.error("Details : " + paymentHistory.toString());
        }

    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
}
