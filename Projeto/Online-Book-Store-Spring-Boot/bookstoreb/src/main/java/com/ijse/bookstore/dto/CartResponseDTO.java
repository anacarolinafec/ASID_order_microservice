package com.ijse.bookstore.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartResponseDTO {
    private long id;
    private String createdDate;
    private List<CartItemsResponseDTO> cartItems;
}
