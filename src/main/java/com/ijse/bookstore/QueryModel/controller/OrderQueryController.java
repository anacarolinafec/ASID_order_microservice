package com.ijse.bookstore.QueryModel.controller;

import com.ijse.bookstore.QueryModel.entity.OrderQuery;
import com.ijse.bookstore.QueryModel.service.OrderServiceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderQueryController {
    
    @Autowired
    private OrderServiceQuery orderServiceQuery;


    @GetMapping("/user/{userId}")
    public ResponseEntity<OrderQuery> getOrderByUserId(@PathVariable Long userId) {
        OrderQuery order = orderServiceQuery.getOrderByUserId(userId);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
