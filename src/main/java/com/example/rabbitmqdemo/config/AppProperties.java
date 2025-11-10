package com.example.rabbitmqdemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String exchange;

    private Routing routing = new Routing();

    public static class Routing {
        private String created;
        private String paid;
        private String email;
        private String confirmed;

        public String getCreated() { return created; }
        public void setCreated(String created) { this.created = created; }

        public String getPaid() { return paid; }
        public void setPaid(String paid) { this.paid = paid; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getConfirmed() { return confirmed; }
        public void setConfirmed(String confirmed) { this.confirmed = confirmed; }
    }

    public String getExchange() { return exchange; }
    public void setExchange(String exchange) { this.exchange = exchange; }

    public Routing getRouting() { return routing; }
    public void setRouting(Routing routing) { this.routing = routing; }
}
