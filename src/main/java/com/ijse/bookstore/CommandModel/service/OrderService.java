package com.ijse.bookstore.CommandModel.service;


import com.ijse.bookstore.CommandModel.dto.NewOrderDTO;
import com.ijse.bookstore.CommandModel.entity.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    Order createOrderDetails(NewOrderDTO newOrderDTO);
    Order getOrderByUserId(Long userId);
}
