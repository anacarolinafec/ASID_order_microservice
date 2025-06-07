package com.ijse.bookstore.CommandModel.dto;

import com.ijse.bookstore.QueryModel.entity.OrderDetailsQuery;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderQueryDTO {

    private Long orderId;
    private Date orderDate;
    private double totalPrice;
    private Long userId;
    private List<OrderDetailsQueryDTO> orderDetails;

    }
