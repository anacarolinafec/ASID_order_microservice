package com.ijse.bookstore.QueryModel.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigQuery {

    public static final String ORDER_QUERY_QUEUE = "order.query.queue";
    public static final String ORDER_QUERY_EXCHANGE = "order.query.exchange";
    public static final String ORDER_QUERY_ROUTING_KEY = "order.query.routingkey";

    @Bean
    public Queue orderQueryQueue() {
        return new Queue(ORDER_QUERY_QUEUE);
    }

    @Bean
    public TopicExchange orderQueryExchange() {
        return new TopicExchange(ORDER_QUERY_EXCHANGE);
    }

    @Bean
    public Binding orderQueryBinding(Queue orderQueryQueue, TopicExchange orderQueryExchange) {
        return BindingBuilder
                .bind(orderQueryQueue)
                .to(orderQueryExchange)
                .with(ORDER_QUERY_ROUTING_KEY);
    }

    @Bean(name = "queryJsonMessageConverter")
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
