package com.ijse.bookstore.QueryModel.controller;

import com.ijse.bookstore.QueryModel.entity.OrderQuery;
import com.ijse.bookstore.QueryModel.service.OrderServiceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class OrderQueryController {
    
    @Autowired
    private OrderServiceQuery orderServiceQuery;


    @GetMapping("/user/{userId}")
    public ResponseEntity<OrderQuery> getOrderByUserId(@PathVariable Long userId) {
        OrderQuery order = orderServiceQuery.getOrderByUserId(userId);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/order/history/user/{userId}")
    public ResponseEntity<List<OrderQuery>> getOrderHistoryForUser(
            @PathVariable Long userId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        try {
            List<OrderQuery> orderHistory = orderServiceQuery.getOrderHistoryByUserIdAndDateRange(userId, startDate, endDate);

            if (orderHistory.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204: Requisição bem sucedida, mas sem conteúdo para retornar
            }
            return new ResponseEntity<>(orderHistory, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Se o serviço lançar IllegalArgumentException para parâmetros inválidos (ex: startDate > endDate)
            return ResponseEntity.badRequest().body(null); // Ou uma mensagem de erro mais específica
        }
        // Considera um tratamento de exceção mais genérico para outros possíveis erros no serviço.
    }
}
