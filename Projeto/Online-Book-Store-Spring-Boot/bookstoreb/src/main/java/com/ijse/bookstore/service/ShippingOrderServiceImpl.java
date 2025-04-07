package com.ijse.bookstore.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.ijse.bookstore.dto.OrderShippingConfirmation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijse.bookstore.entity.ShippingOrder;
import com.ijse.bookstore.repository.ShippingOrderRepository;

@Service
public class ShippingOrderServiceImpl implements ShippingOrderService {
    
    @Autowired
    private ShippingOrderRepository shippingOrderRepository;

    @Override
    public ShippingOrder createShippingOrder(OrderShippingConfirmation orderShippingConfirmation){
        var shippingOrder = new ShippingOrder();
        shippingOrder.setOrderId(orderShippingConfirmation.getOrderId());
        shippingOrder.setUserId(orderShippingConfirmation.getUserId());
        shippingOrder.setAddress(orderShippingConfirmation.getAddress());
        shippingOrder.setCity(orderShippingConfirmation.getCity());
        shippingOrder.setPostalCode(orderShippingConfirmation.getPostalCode());
        shippingOrder.setShippingDate(LocalDate.now());
        shippingOrder.setTrackingNumber(UUID.randomUUID().toString());

        return shippingOrderRepository.save(shippingOrder);
    }

    public List<ShippingOrder> getAllShippingOrders(){
        return shippingOrderRepository.findAll();
    }
}
