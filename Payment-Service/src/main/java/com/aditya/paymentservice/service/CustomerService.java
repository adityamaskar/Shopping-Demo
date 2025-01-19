package com.aditya.paymentservice.service;

import com.aditya.paymentservice.dto.CustomerDTO;
import com.aditya.paymentservice.entity.Customer;
import com.aditya.paymentservice.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    public final CustomerRepo customerRepo;


    public Customer addCustomer(CustomerDTO customerDTO) {
        return customerRepo.save(Customer.builder()
                .name(customerDTO.getName())
                .email(customerDTO.getEmail())
                .phone(customerDTO.getPhone())
                .address(customerDTO.getAddress())
                .city(customerDTO.getCity())
                .balanceAmount(customerDTO.getBalanceAmount())
                .build());
    }

    public List<Customer> addAllCustomers(List<CustomerDTO> customerDTOs) {
        List<Customer> customers = customerDTOs.stream()
                .map(customerDTO -> Customer.builder()
                        .name(customerDTO.getName())
                        .email(customerDTO.getEmail())
                        .phone(customerDTO.getPhone())
                        .address(customerDTO.getAddress())
                        .city(customerDTO.getCity())
                        .balanceAmount(customerDTO.getBalanceAmount())
                        .build())
                .toList();
        customerRepo.saveAll(customers);
        return customers;
    }
}
