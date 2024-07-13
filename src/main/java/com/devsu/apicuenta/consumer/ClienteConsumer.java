package com.devsu.apicuenta.consumer;


import com.devsu.apicuenta.dto.ConsultaMovimientosResponseDTO;
import com.devsu.apicuenta.dto.rabbit.ClienteEvent;
import com.devsu.apicuenta.exception.MovimientosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class ClienteConsumer {

    private final ConsultaMovimientosResponseDTO consultaMovimientosResponseDTO;


    @Autowired
    public ClienteConsumer(ConsultaMovimientosResponseDTO consultaMovimientosResponseDTO){
        this.consultaMovimientosResponseDTO=consultaMovimientosResponseDTO;
    }

    private RabbitTemplate rabbitTemplate;
    private Logger LOGGER = LoggerFactory.getLogger(ClienteConsumer.class);

    @RabbitListener(queues = "${rabbitmq.queue.cliente.name}")
    public void consume(ClienteEvent event){
        if(Objects.isNull(event.getCliente())) throw new MovimientosException();
        LOGGER.info(String.format("Evento recibido de Cliente => %s", event.toString()));

        Map<String, String> environment = System.getenv();

        LOGGER.info("Mensaje de Cliente: "+event.getMessage());
        LOGGER.info("Cliente: " + event.getCliente().getNombre());
        LOGGER.info("DNI: " + event.getCliente().getDni());
        LOGGER.info("**********************************************************");
        this.consultaMovimientosResponseDTO.getMovimientos().forEach(m->{
            LOGGER.info("===========================================");
            LOGGER.info("Fecha: " + m.getFecha());
            LOGGER.info("Num CUenta: " + m.getNumCuenta());
            LOGGER.info("Tipo Cuenta: " + m.getTipoCuenta());
            LOGGER.info("Saldo Inicial: " + m.getSaldoInicial());
            LOGGER.info("Saldo Disponible: " + m.getGetSaldoDisponible());
            LOGGER.info("Estado: " + m.getEstado());
            LOGGER.info("Movimiento: " + m.getValorMovimiento());
            LOGGER.info("===========================================");
        });
    }
}
