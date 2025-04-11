package com.ijse.bookstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ijse.bookstore.client.CartServiceHTTPClient;
import com.ijse.bookstore.client.UserServiceHTTPClient;
import com.ijse.bookstore.dto.*;
import com.ijse.bookstore.entity.Order;
import com.ijse.bookstore.entity.OrderDetails;
import com.ijse.bookstore.producer.MessageProducer;
import com.ijse.bookstore.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private MessageProducer messageProducer;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserServiceHTTPClient userServiceHTTPClient;
    @Autowired
    CartServiceHTTPClient cartServiceHTTPClient;

    public Order createOrderDetails(NewOrderDTO newOrderDTO) {

        //Faz um pedido HTTP ao monolitico para buscar o objeto user atraves do id do user recebido
        UserResponseDTO user = userServiceHTTPClient.getUserById(newOrderDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User nao existe"));

        //Faz um pedido HTTP ao monolitico para buscar o objeto cart atraves do id do user recebido
        // devolve um carrinho completo do utilizador, incluindo a lista de cart items (lista definida na classe cart)
        CartResponseDTO cart = cartServiceHTTPClient.getCartOfUserId(newOrderDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Cart nao existe"));

        //Para cada item no carrinho, é criado um OrderDetail (bookid, quantity, subtotal...)
        List<OrderDetails> orderDetails = createOrderDetailsFromCartItems(cart.getCartItems(), user);

        //Criar uma order e dar os sets
        Order order = new Order();
        order.setOrderDate(Date.from(Instant.now()));
        order.setOrderDetails(orderDetails);
        order.setTotalPrice(calculateTotalPrice(orderDetails));
        order.setUserId(user.getId());

        //guardar a nova ordem no repositorio de orders
        order = orderRepository.save(order);

        // criando a order, e logo criado o shipping
        var orderShippingConfirmation = new OrderShippingConfirmation();
        orderShippingConfirmation.setOrderId(order.getId());
        orderShippingConfirmation.setUserId(user.getId());
        orderShippingConfirmation.setOrderTotal(order.getTotalPrice());
        orderShippingConfirmation.setAddress(newOrderDTO.getAddress());
        orderShippingConfirmation.setCity(newOrderDTO.getCity());
        orderShippingConfirmation.setPostalCode(newOrderDTO.getPostalCode());

        try {
            var orderShippingConfirmationJson = objectMapper.writeValueAsString(orderShippingConfirmation);
            messageProducer.sendMessage(orderShippingConfirmationJson);
        } catch (JsonProcessingException e) {
            log.error("Error happened on sending object on rabbit mq");
            throw new RuntimeException(e);
        }

        return order;
    }

    // percorre a lista de cartItems e para cada cartItem cria uma orderdetail
    private List<OrderDetails> createOrderDetailsFromCartItems(List<CartItemsResponseDTO> cartItems, UserResponseDTO user) {
        List<OrderDetails> orderDetails = new ArrayList<>();
        //É criada uma lista

       //pecorre a lista de cartItems
        for(var cartItem: cartItems){
            //obtem o book/cartitem
            var book = cartItem.getBookid();

            //cria um orderdetail e dar os sets necessarios
            var orderDetail = new OrderDetails();
            orderDetail.setBookId(book.getId());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setSubTotal(book.getPrice() * cartItem.getQuantity());
            orderDetail.setUserId(user.getId());

            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }

    private double calculateTotalPrice (List<OrderDetails> orderDetails){

        if(orderDetails.isEmpty()){
            return 0;
        }

        double sum = 0;
        for (var orderDetail: orderDetails){
            sum += orderDetail.getSubTotal();
        }
        return sum/orderDetails.size();
    }
}
