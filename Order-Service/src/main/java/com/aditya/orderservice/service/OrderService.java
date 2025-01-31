package com.aditya.orderservice.service;

import com.aditya.orderservice.dto.OrderDTO;
import com.aditya.orderservice.entity.Order;
import com.aditya.orderservice.entity.Status;
import com.aditya.orderservice.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


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

    @KafkaListener(topics = "InventoryFailed", groupId = "order-group", containerFactory = "kafkaListenerContainerFactoryOrder")
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

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
}
