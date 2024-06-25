package br.com.oobj.billing.broker.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${oobj.billing.fila.recepcao:billing_recepcao}")
    private String filaRecepcao;

    @Value("${oobj.billing.fila.update:billing_update}")
    private String filaUpdate;

    @Value("${oobj.billing.fila.descarte:billing_descarte}")
    private String filaDescarte;

    @Bean
    public Queue billingRecepcaoQueue() {
        return new Queue(filaRecepcao, true);
    }

    @Bean
    public Queue billingUpdateQueue() {
        return new Queue(filaUpdate, true);
    }

    @Bean
    public Queue billingDescarteQueue() {
        return new Queue(filaDescarte, true);
    }

}
