package com.example.rabbitmqdemo.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConfirmWorker {

    @RabbitListener(queues = "orders.confirmed.q")
    public void onConfirmed(Map<String, Object> msg) {
        System.out.println("\n[CONFIRM] ✅ Orden confirmada");
        System.out.println("Cliente : " + msg.get("customer"));
        System.out.println("Monto   : " + msg.get("amount"));
        System.out.println("OrderID : " + msg.get("orderId"));
        System.out.println("Status  : " + msg.get("status"));
    }
}
