package com.aditya.inventoryservice;

import com.aditya.inventoryservice.entity.Product;
import com.aditya.inventoryservice.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryServiceApplication //implements CommandLineRunner
{

//    @Autowired
//    private ProductRepo productRepo;

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        productRepo.save(new Product(1L, "Laptop", "Dell Laptop", 85000.0, 10));
//    }
}
