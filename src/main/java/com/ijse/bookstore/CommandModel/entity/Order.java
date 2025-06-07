package com.ijse.bookstore.CommandModel.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "customer_order")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date orderDate;

    //@Column
    //private String State;

    @Column
    private double totalPrice;

    @Column
    private long userId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderDetails> orderDetails;
    //uma order detail esta para um cartitem e um cartItem apenas (dai ter associado a si um livro - bookID - enves de uma lista de livros)
    // uma order e a juncao de todos os cartitems ou seja, e uma lista de orderdetails

}
