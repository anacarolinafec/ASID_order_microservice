package com.ijse.bookstore.QueryModel.service;

import com.ijse.bookstore.CommandModel.dto.OrderDetailsQueryDTO;
import com.ijse.bookstore.CommandModel.producer.MessageProducer;
import com.ijse.bookstore.QueryModel.dto.OrderQueryRecievedDTO;
import com.ijse.bookstore.QueryModel.entity.OrderDetailsQuery;
import com.ijse.bookstore.QueryModel.entity.OrderQuery;
import com.ijse.bookstore.QueryModel.repository.OrderQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class OrderServiceQueryImpl implements OrderServiceQuery {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceQueryImpl.class);

    @Autowired
    private MessageProducer messageProducer;
    @Autowired
    private OrderQueryRepository orderqueryRepository;


    @Override
    public void createOrderFromMessage(OrderQueryRecievedDTO orderQueryRecievedDTO) {
        OrderQuery order = new OrderQuery();
        order.setId(orderQueryRecievedDTO.getOrderId());
        order.setUserId(orderQueryRecievedDTO.getUserId());
        order.setOrderDate(orderQueryRecievedDTO.getOrderDate());
        order.setTotalPrice(orderQueryRecievedDTO.getTotalPrice());

        List<OrderDetailsQuery> detailsList = new ArrayList<>();

        for (OrderDetailsQueryDTO detailsDTO : orderQueryRecievedDTO.getOrderDetails()) {
            OrderDetailsQuery details = new OrderDetailsQuery();
            details.setOrder(order);
            details.setBookId(detailsDTO.getBookId());
            details.setQuantity(detailsDTO.getQuantity());
            details.setSubTotal(detailsDTO.getSubTotal());

            detailsList.add(details);
        }

        order.setOrderDetails(detailsList);

        orderqueryRepository.save(order);
    }

    @Override
    public OrderQuery getOrderByUserId(Long userId) {
        List<OrderQuery> orders = orderqueryRepository.findByUserIdWithDetails(userId);

        if (orders.isEmpty()) {
            throw new IllegalArgumentException("Order não existe");
        }

        // Devolve a encomenda mais recente
        orders.sort(Comparator.comparing(OrderQuery::getOrderDate).reversed());
        return orders.get(0);
    }

}
