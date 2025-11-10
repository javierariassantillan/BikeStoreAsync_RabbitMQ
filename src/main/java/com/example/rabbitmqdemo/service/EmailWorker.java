package com.example.rabbitmqdemo.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmailWorker {

    @RabbitListener(queues = "orders.emails.q")
    public void onEmail(Map<String, Object> msg) {
        System.out.println("\n[EMAIL] Enviando correo para el pedido:");
        System.out.println("Cliente : " + msg.get("customer"));
        System.out.println("Monto   : " + msg.get("amount"));
        System.out.println("OrderID : " + msg.get("orderId"));
    }
}
