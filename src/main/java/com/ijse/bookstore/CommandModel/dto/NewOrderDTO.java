package com.ijse.bookstore.CommandModel.dto;

import lombok.Data;

@Data
public class NewOrderDTO {
    private long userId;
    private String address;
    private String city;
    private String postalCode;
}
