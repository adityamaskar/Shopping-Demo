package com.aditya.orderservice.service;

import com.aditya.orderservice.dto.OrderDTO;
import com.aditya.orderservice.entity.Order;
import com.aditya.orderservice.entity.Status;
import com.aditya.orderservice.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        kafkaTemplateOrder.send("order-service-event", orderDTO);
        log.info("Order sent to Kafka topic");
        return save.getId();
    }
}
