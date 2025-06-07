package com.ijse.bookstore.CommandModel.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CartItemsResponseDTO {
    private long id;
    private int quantity;
    private double unitPrice;
    private double subTotal;
    private Long bookid;
}
