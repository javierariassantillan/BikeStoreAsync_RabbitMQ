package com.example.rabbitmqdemo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // Exchange topic
    @Bean
    public TopicExchange demoExchange(AppProperties props) {
        return new TopicExchange(props.getExchange(), true, false);
    }

    // Queues
    @Bean
    public Queue paymentsQueue() {                // order.created
        return QueueBuilder.durable("orders.payments.q").build();
    }

    @Bean
    public Queue emailsQueue() {                  // order.email
        return QueueBuilder.durable("orders.emails.q").build();
    }

    @Bean
    public Queue confirmQueue() {                 // order.confirmed
        return QueueBuilder.durable("orders.confirmed.q").build();
    }

    // Bindings
    @Bean
    public Binding bindPayments(Queue paymentsQueue, TopicExchange demoExchange, AppProperties props) {
        return BindingBuilder.bind(paymentsQueue)
                .to(demoExchange)
                .with(props.getRouting().getCreated());
    }

    @Bean
    public Binding bindEmails(Queue emailsQueue, TopicExchange demoExchange, AppProperties props) {
        return BindingBuilder.bind(emailsQueue)
                .to(demoExchange)
                .with(props.getRouting().getEmail());
    }

    @Bean
    public Binding bindConfirm(Queue confirmQueue, TopicExchange demoExchange, AppProperties props) {
        return BindingBuilder.bind(confirmQueue)
                .to(demoExchange)
                .with(props.getRouting().getConfirmed());
    }

    // 👇 Forzar JSON para evitar SecurityException con HashMap
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
