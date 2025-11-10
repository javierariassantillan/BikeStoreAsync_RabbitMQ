package com.example.rabbitmqdemo.service;

import com.example.rabbitmqdemo.config.AppProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PaymentWorker {

    private final RabbitTemplate rabbitTemplate;
    private final AppProperties props;

    public PaymentWorker(RabbitTemplate rabbitTemplate, AppProperties props) {
        this.rabbitTemplate = rabbitTemplate;
        this.props = props;
    }

    @RabbitListener(queues = "orders.payments.q")
    public void onCreated(Map<String, Object> msg) {
        System.out.println("\n[PAYMENT] Procesando pago...");
        System.out.println("Cliente : " + msg.get("customer"));
        System.out.println("Monto   : " + msg.get("amount"));
        System.out.println("OrderID : " + msg.get("orderId"));

        // Simula pago OK
        Map<String, Object> paid = new HashMap<>(msg);
        paid.put("status", "PAID");

        // 1) Enviar a cola de emails
        rabbitTemplate.convertAndSend(
                props.getExchange(),
                props.getRouting().getEmail(),
                paid
        );
        System.out.println("[PAYMENT] Pago confirmado. Reenviado a '" + props.getRouting().getEmail() + "'.");

        // 2) Enviar confirmación final
        rabbitTemplate.convertAndSend(
                props.getExchange(),
                props.getRouting().getConfirmed(),
                paid
        );
        System.out.println("[PAYMENT] Reenviado a '" + props.getRouting().getConfirmed() + "'.");
    }
}

