package com.ijse.bookstore.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ijse.bookstore.dto.OrderShippingConfirmation;
import com.ijse.bookstore.service.ShippingOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ShippingOrderConsumer {

    private final ShippingOrderService shippingOrderService;
    private final ObjectMapper objectMapper;

    public ShippingOrderConsumer(ShippingOrderService shippingOrderService, ObjectMapper objectMapper) {
        this.shippingOrderService = shippingOrderService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "shipping.queue")
    public void receiveOrderConfirmation(String orderShippingConfirmationJson) {
        log.info("operation='receiveOrderConfirmation', message='Received order confirmation', ordershippingConfirmation={}", orderShippingConfirmationJson);

        OrderShippingConfirmation orderShippingConfirmation = null;
        try {
            orderShippingConfirmation = objectMapper.readValue(orderShippingConfirmationJson, OrderShippingConfirmation.class);
        } catch (JsonProcessingException e) {
            log.error("operation='receiveOrderConfirmation', message='Error converting the order from json'");
            throw new RuntimeException(e);
        }
        shippingOrderService.createShippingOrder(orderShippingConfirmation);
        log.info("operation='receiveOrderConfirmation', message='Processed order confirmation'");
    }
}
