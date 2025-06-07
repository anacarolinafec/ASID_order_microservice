package com.ijse.bookstore.CommandModel.dto;

import lombok.Data;

@Data
public class OrderShippingConfirmation {
    private Long orderId;
    private Long userId;
    private double orderTotal;
    private String address;
    private String city;
    private String postalCode;
}
