package com.example.rabbitmqdemo.service;

import com.example.rabbitmqdemo.config.AppProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    private final RabbitTemplate rabbitTemplate;
    private final AppProperties props;

    public Producer(RabbitTemplate rabbitTemplate, AppProperties props, MessageConverter messageConverter) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setMessageConverter(messageConverter); // JSON 🔥
        this.props = props;
    }

    public void send(Object payload) {
        rabbitTemplate.convertAndSend(
                props.getExchange(),
                props.getRouting().getCreated(),
                payload
        );
        System.out.println("[PRODUCER] Enviado a " + props.getRouting().getCreated());
    }
}
