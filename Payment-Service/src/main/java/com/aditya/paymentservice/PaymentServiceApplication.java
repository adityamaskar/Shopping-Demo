package com.aditya.paymentservice;

import com.aditya.paymentservice.dto.CustomerDTO;
import com.aditya.paymentservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class PaymentServiceApplication implements CommandLineRunner {

    private final CustomerService customerService;

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//        List<CustomerDTO> customerDTOList = List.of(
//                CustomerDTO.builder()
//                        .name("John Doe")

    }
}
