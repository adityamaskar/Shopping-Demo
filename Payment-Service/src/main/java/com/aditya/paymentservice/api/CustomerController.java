package com.aditya.paymentservice.api;

import com.aditya.paymentservice.dto.CustomerDTO;
import com.aditya.paymentservice.entity.Customer;
import com.aditya.paymentservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    public final CustomerService customerService;

    @PostMapping("/add")
    public void addCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = customerService.addCustomer(customerDTO);
        log.info("Customer added with id: " + customer.getId());
    }

    @PostMapping("/add-all")
    public List<Customer> addAllCustomers(@RequestBody List<CustomerDTO> customerDTOs) {
        List<Customer> customers = customerService.addAllCustomers(customerDTOs);
        log.info("All customers added");
        return customers;
    }

    @GetMapping("/all")
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }
}
