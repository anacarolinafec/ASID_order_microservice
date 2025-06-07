package com.ijse.bookstore.CommandModel.controller;


import com.ijse.bookstore.CommandModel.dto.NewOrderDTO;
import com.ijse.bookstore.CommandModel.entity.Order;
import com.ijse.bookstore.CommandModel.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
