package com.ijse.bookstore.controller;

import com.ijse.bookstore.dto.NewOrderDTO;
import com.ijse.bookstore.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ijse.bookstore.service.OrderService;

import java.util.List;

@RestController
public class OrderController {
    
    @Autowired
    private OrderService orderService;


    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody NewOrderDTO newOrderDTO) {

        Order orderedDetails = orderService.createOrderDetails(newOrderDTO);
        // criar um objeto do tipo order atraves do orderdto recebido

        return new ResponseEntity<>(orderedDetails,HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Order> getOrderByUserId(@PathVariable Long userId) {
        Order order = orderService.getOrderByUserId(userId);

        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
