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
import java.util.Date;
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

    @Override
    public List<OrderQuery> getOrderHistoryByUserIdAndDateRange(Long userId, Date startDate, Date endDate) {
        // Validações básicas (importante para evitar erros e queries desnecessárias)
        if (userId == null) {
            log.warn("Attempted to get order history with null userId.");
            throw new IllegalArgumentException("User ID must not be null.");
        }
        if (startDate == null) {
            log.warn("Attempted to get order history for userId {} with null startDate.", userId);
            throw new IllegalArgumentException("Start date must not be null.");
        }
        if (endDate == null) {
            log.warn("Attempted to get order history for userId {} with null endDate.", userId);
            throw new IllegalArgumentException("End date must not be null.");
        }
        if (startDate.after(endDate)) {
            log.warn("Attempted to get order history for userId {} with startDate after endDate ({} > {}).", userId, startDate, endDate);
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        log.info("Fetching order history for userId: {} between {} and {}", userId, startDate, endDate);
        List<OrderQuery> orders = orderqueryRepository.findByUserIdAndOrderDateBetween(userId, startDate, endDate);
        log.info("Found {} orders for userId: {} in the given date range.", orders.size(), userId);
        return orders;
    }
}
