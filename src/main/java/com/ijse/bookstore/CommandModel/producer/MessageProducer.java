package com.ijse.bookstore.CommandModel.producer;

import com.ijse.bookstore.CommandModel.dto.OrderQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.shipping.exchange}")
    private String shippingExchange;

    @Value("${rabbitmq.shipping.routingkey}")
    private String shippingRoutingKey;

    @Value("${rabbitmq.query.exchange}")
    private String queryExchange;

    @Value("${rabbitmq.query.routingkey}")
    private String queryRoutingKey;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrderToShipping(String message) {
        log.info("operation='sendOrderShippingConfirmation', message='{}'", message);
        rabbitTemplate.convertAndSend(shippingExchange, shippingRoutingKey, message);
    }

    public void sendOrderToQueryModel(OrderQueryDTO dto) {
        log.info("operation='sendOrderToQueryModel', orderId='{}'", dto.getOrderId());
        rabbitTemplate.convertAndSend(queryExchange, queryRoutingKey, dto);
    }
}
