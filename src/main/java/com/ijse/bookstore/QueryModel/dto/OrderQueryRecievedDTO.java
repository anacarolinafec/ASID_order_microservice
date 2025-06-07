package com.ijse.bookstore.QueryModel.dto;

import com.ijse.bookstore.CommandModel.dto.OrderDetailsQueryDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderQueryRecievedDTO {

    private Long orderId;
    private Date orderDate;
    private double totalPrice;
    private Long userId;
    private List<OrderDetailsQueryDTO> orderDetails;
}
