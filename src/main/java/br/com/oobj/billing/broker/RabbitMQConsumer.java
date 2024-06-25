package br.com.oobj.billing.broker;

import br.com.oobj.billing.exceptions.DataTypeNaoInformadoException;
import br.com.oobj.billing.exceptions.IdRequisicaoNaoInformadoException;
import br.com.oobj.billing.service.BillingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class RabbitMQConsumer {

    private final BillingService service;

    private final RabbitTemplate rabbitTemplate;

    private final Queue billingDescarteQueue;

    @RabbitListener(queues = "#{billingRecepcaoQueue.name}", ackMode = "MANUAL")
    public void recepcaoMessage(Message message) {
        log.info("Processando mensagem da fila de recepcao: {}", message);
        try {
            service.newRecepcaoMessage(message);
        } catch (Exception e) {
            descartarMensagem(message, e);
        }
    }

    @RabbitListener(queues = "#{billingUpdateQueue.name}", ackMode = "MANUAL")
    public void updateMessage(Message message) {
        log.info("Processando mensagem da fila de update: {}", message);
        try {
            service.newUpdateMessage(message);
        } catch (Exception e) {
            descartarMensagem(message, e);
        }
    }

    public void descartarMensagem(Message mensagem, Exception e) {
        log.error(e.getMessage() + " Movendo para fila de descarte.");
        mensagem.getMessageProperties().setHeader("motivo_descarte", e.getMessage());
        rabbitTemplate.send(billingDescarteQueue.getName(), mensagem);
    }


}