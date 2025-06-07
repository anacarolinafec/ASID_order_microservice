package com.ijse.bookstore.CommandModel.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) //se aparecer no body da response coisas que nao aparecem na classe, ignora
@Data
public class CartResponseDTO {
    private long id;
    private String createdDate;
    private List<CartItemsResponseDTO> cartItems;
}
