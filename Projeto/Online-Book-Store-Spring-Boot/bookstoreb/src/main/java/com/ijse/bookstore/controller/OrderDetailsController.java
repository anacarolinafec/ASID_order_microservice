package com.ijse.bookstore.controller;

import com.ijse.bookstore.dto.NewOrderDTO;
import com.ijse.bookstore.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ijse.bookstore.service.OrderDetailsService;


@RestController
public class OrderDetailsController {
    
    @Autowired
    private OrderDetailsService orderDetailsService;

    @PostMapping("/orderdetails")
    public ResponseEntity<Order> createOrder(@RequestBody NewOrderDTO newOrderDTO) {

        Order orderedDetails = orderDetailsService.createOrderDetails(newOrderDTO);

        return new ResponseEntity<>(orderedDetails,HttpStatus.CREATED);
    }
}
