package com.ijse.bookstore.service;

import com.ijse.bookstore.dto.NewOrderDTO;
import com.ijse.bookstore.entity.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    Order createOrderDetails(NewOrderDTO newOrderDTO);
    Order getOrderByUserId(Long userId);

}
