package com.devsu.apicuenta.consumer;


import com.devsu.apicuenta.dto.rabbit.ClienteDTO;
import com.devsu.apicuenta.dto.rabbit.ClienteEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {


    private RabbitTemplate rabbitTemplate;
    private Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    @RabbitListener(queues = "${rabbitmq.queue.cliente.name}")
    public void consume(ClienteEvent event){
        LOGGER.info(String.format("Order event received => %s", event.toString()));

        // save order event data in database



    }
}
