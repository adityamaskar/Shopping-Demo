package com.aditya.inventoryservice.api;

import com.aditya.inventoryservice.entity.Product;
import com.aditya.inventoryservice.service.InventoryService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/add")
    public void addProducts(@RequestBody List<Product> products) {
        log.info("Adding products to inventory");
        products.forEach(product -> log.info("Product: {}", product));
        inventoryService.addProducts(products);
        log.info("Products added to inventory");
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable Long id) {
        log.info("Getting product by id: {}", id);
        return inventoryService.getProductById(id);
    }
}
