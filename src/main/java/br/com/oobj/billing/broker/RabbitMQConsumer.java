package br.com.oobj.billing.broker;

import br.com.oobj.billing.exceptions.DataNotFoundException;
import br.com.oobj.billing.service.BillingService;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
@Component
public class RabbitMQConsumer {

    private final BillingService service;

    private final RabbitTemplate rabbitTemplate;

    private final Queue billingDescarteQueue;

    @RabbitListener(queues = "#{billingRecepcaoQueue.name}", ackMode = "MANUAL")
    public void recepcaoMessage(Message message, Channel channel) {
        log.info("Processando mensagem da fila de recepcao: {}", message);
        try {
            service.newRecepcaoMessage(message);
            registerAck(message, channel);
        } catch (Exception e) {
            descartarMensagem(message, e, channel);
        }
    }

    @RabbitListener(queues = "#{billingUpdateQueue.name}", ackMode = "MANUAL")
    public void updateMessage(Message message, Channel channel) {
        log.info("Processando mensagem da fila de update: {}", message);
        try {
            service.newUpdateMessage(message);
            registerAck(message, channel);
        } catch (DataNotFoundException e) {
            log.warn("{}. A mensagem sera reenfileirada para ser processada posteriormente.", e.getMessage());
            try {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            } catch (IOException ioException) {
                log.error("Falha ao reenfileirar a mensagem: {}", ioException.getMessage());
            }
        } catch (Exception e) {
            descartarMensagem(message, e, channel);
        }
    }

    public void descartarMensagem(Message mensagem, Exception e, Channel channel) {
        log.error("Movendo para fila de descarte. Motivo: ", e.getMessage());
        e.printStackTrace();
        mensagem.getMessageProperties().setHeader("motivo_descarte", e.getMessage());
        rabbitTemplate.send(billingDescarteQueue.getName(), mensagem);
        try {
            channel.basicAck(mensagem.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException ioException) {
            log.error("Falha ao registrar o acknowledgement da mensagem: {}", ioException.getMessage());
        }
    }

    private static void registerAck(Message message, Channel channel) throws IOException {
        log.info("Mensagem processada. Irei registrar o acknowledgement.");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }


}