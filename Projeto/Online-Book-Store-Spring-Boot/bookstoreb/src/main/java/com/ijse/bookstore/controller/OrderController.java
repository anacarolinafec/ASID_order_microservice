package com.ijse.bookstore.controller;

import com.ijse.bookstore.dto.NewOrderDTO;
import com.ijse.bookstore.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ijse.bookstore.service.OrderService;

@RestController
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    @PostMapping("/order") // criar uma order no order-service
    public ResponseEntity<Order> createOrder(@RequestBody NewOrderDTO newOrderDTO) {

        Order orderedDetails = orderService.createOrderDetails(newOrderDTO);

        return new ResponseEntity<>(orderedDetails,HttpStatus.CREATED);
    }
}
