package com.ijse.bookstore.QueryModel.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ijse.bookstore.QueryModel.dto.OrderQueryRecievedDTO;
import com.ijse.bookstore.QueryModel.service.OrderServiceQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderQueryListener {

    private final OrderServiceQuery orderServiceQuery;
    private final ObjectMapper objectMapper;

    public OrderQueryListener(OrderServiceQuery orderServiceQuery, ObjectMapper objectMapper) {
        this.orderServiceQuery = orderServiceQuery;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "order.query.queue")
    public void receiveOrderQuery(String orderQueryJson) {
        log.info("operation='receiveOrderQuery', message='Received order data', orderQuery={}", orderQueryJson);

        OrderQueryRecievedDTO orderQueryDTO = null;
        try {
            orderQueryDTO = objectMapper.readValue(orderQueryJson, OrderQueryRecievedDTO.class);
        } catch (JsonProcessingException e) {
            log.error("operation='receiveOrderQuery', message='Error converting JSON to OrderQueryRecievedDTO'");
            throw new RuntimeException("Erro ao converter JSON para OrderQueryRecievedDTO", e);
        }

        orderServiceQuery.createOrderFromMessage(orderQueryDTO);

        log.info("operation='receiveOrderQuery', message='Successfully processed order data'");
    }
}
