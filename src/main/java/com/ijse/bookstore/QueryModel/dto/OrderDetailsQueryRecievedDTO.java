package com.ijse.bookstore.QueryModel.dto;

import lombok.Data;

@Data
public class OrderDetailsQueryRecievedDTO {

    private Long orderDetailId;
    private int quantity;
    private double subTotal;
    private Long bookId;
    private Long userId;
    private Long orderId;
}
