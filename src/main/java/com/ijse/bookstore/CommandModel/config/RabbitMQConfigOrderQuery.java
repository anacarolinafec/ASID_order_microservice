package com.ijse.bookstore.CommandModel.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;


@Configuration
public class RabbitMQConfigOrderQuery {

    public static final String ORDER_QUERY_QUEUE = "order.query.queue";
    public static final String ORDER_QUERY_EXCHANGE = "order.query.exchange";
    public static final String ORDER_QUERY_ROUTING_KEY = "order.query.routingkey";

    @Bean(name = "commandOrderQueryQueue")
    public Queue orderQueryQueue() {
        return new Queue(ORDER_QUERY_QUEUE);
    }

    @Bean(name = "commandOrderQueryExchange")
    public TopicExchange orderQueryExchange() {
        return new TopicExchange(ORDER_QUERY_EXCHANGE);
    }

    @Bean(name = "commandOrderJsonMessageConverter") // renomeado para evitar conflitos
    public Jackson2JsonMessageConverter orderQueryJsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            @Qualifier("commandOrderJsonMessageConverter") Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }

    @Bean(name = "commandOrderQueryBinding")
    public Binding orderQueryBinding(Queue orderQueryQueue, TopicExchange orderQueryExchange) {
        return BindingBuilder
                .bind(orderQueryQueue)
                .to(orderQueryExchange)
                .with(ORDER_QUERY_ROUTING_KEY);
    }
}
