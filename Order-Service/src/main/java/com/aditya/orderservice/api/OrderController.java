package com.aditya.orderservice.api;

import com.aditya.orderservice.dto.OrderDTO;
import com.aditya.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place-order")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> placeOrder(@RequestBody OrderDTO orderDTO) {
        Long id = orderService.placeOrder(orderDTO);
        return ResponseEntity.ok("Order placed with id: " + id);
    }
}
