package com.ijse.bookstore.CommandModel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "customer_order_details")
public class OrderDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int quantity;

    @Column
    private double subTotal;

    @Column
    private Long bookId;
    //uma order detail esta para um cartitem e um cartItem apenas (dai ter associado a si um livro - bookID - enves de uma lista de livros)

    @Column
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;
}
