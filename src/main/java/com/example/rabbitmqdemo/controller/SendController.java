package com.example.rabbitmqdemo.controller;

import com.example.rabbitmqdemo.service.Producer;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
public class SendController {

    private final Producer producer;

    public SendController(Producer producer) {
        this.producer = producer;
    }

    // GET de prueba rápida desde navegador
    @GetMapping("/order")
    public Map<String, Object> sendOrder(@RequestParam(defaultValue = "anon") String customer) {
        Map<String, Object> order = Map.of(
                "orderId", "ORD-" + UUID.randomUUID(),
                "customer", customer,
                "amount", 49.9
        );
        producer.send(order);
        return Map.of("status", "ENQUEUED", "order", order);
    }

    // POST para Postman: body JSON
    @PostMapping("/orders")
    public Map<String, Object> createOrder(@RequestBody Map<String, Object> order) {
        if (!order.containsKey("orderId")) {
            order = new java.util.HashMap<>(order);
            ((java.util.HashMap<String, Object>) order)
                    .put("orderId", "ORD-" + UUID.randomUUID());
        }
        producer.send(order);
        return Map.of("status", "ENQUEUED", "order", order);
    }
}
