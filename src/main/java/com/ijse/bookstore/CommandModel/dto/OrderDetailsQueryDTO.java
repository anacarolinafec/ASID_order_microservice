package com.ijse.bookstore.CommandModel.dto;

import lombok.Data;

@Data
public class OrderDetailsQueryDTO {

    private Long orderDetailId;
    private int quantity;
    private double subTotal;
    private Long bookId;
    private Long userId;
    private Long orderId;
}
