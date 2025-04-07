package com.ijse.bookstore.service;

import java.util.List;

import com.ijse.bookstore.dto.OrderShippingConfirmation;
import org.springframework.stereotype.Service;

import com.ijse.bookstore.entity.ShippingOrder;



@Service
public interface ShippingOrderService {
    
    ShippingOrder createShippingOrder(OrderShippingConfirmation orderShippingConfirmation);

    List<ShippingOrder> getAllShippingOrders();
}
