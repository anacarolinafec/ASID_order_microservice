package com.ijse.bookstore.QueryModel.service;


import com.ijse.bookstore.CommandModel.dto.OrderQueryDTO;
import com.ijse.bookstore.QueryModel.dto.OrderQueryRecievedDTO;
import com.ijse.bookstore.QueryModel.entity.OrderQuery;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface OrderServiceQuery {
    OrderQuery getOrderByUserId(Long userId);
    void createOrderFromMessage(OrderQueryRecievedDTO orderQueryRecieved);
    List<OrderQuery> getOrderHistoryByUserIdAndDateRange(Long userId, Date startDate, Date endDate);
}
