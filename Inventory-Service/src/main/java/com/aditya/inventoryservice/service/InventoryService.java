package com.aditya.inventoryservice.service;

import com.aditya.inventoryservice.dto.OrderDTO;
import com.aditya.inventoryservice.entity.Product;
import com.aditya.inventoryservice.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final ProductRepo productRepo;

    private final KafkaTemplate<String, OrderDTO> kafkaTemplateInventory;


    public void addProducts(List<Product> products) {
        productRepo.saveAll(products);
    }

    @KafkaListener(topics = "order-service-event", groupId = "inventory-group", containerFactory = "kafkaListenerContainerFactoryOrder")
    public void createOrderEvent( OrderDTO order) {
        Product product = productRepo.findById(order.getProductId()).orElse(null);
        if(product == null){
            //todo send failure event
        }
        else if(product.getQuantityInStock() < order.getQuantity()){
            //todo send failure event
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
        kafkaTemplateInventory.send("inventory-service-event", order);
        log.info("Order event sent to payment service");
    }

    public Product getProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    }
}
