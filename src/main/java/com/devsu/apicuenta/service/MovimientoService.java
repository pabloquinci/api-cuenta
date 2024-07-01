package com.devsu.apicuenta.service;

import com.devsu.apicuenta.dto.*;
import com.devsu.apicuenta.exception.CuentaNotFoundExcdeption;
import com.devsu.apicuenta.model.Cuenta;
import com.devsu.apicuenta.model.ETipoMovimiento;
import com.devsu.apicuenta.model.Movimiento;
import com.devsu.apicuenta.repository.CuentaRepository;
import com.devsu.apicuenta.repository.MovimientoRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class MovimientoService {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.binding.routing.key}")
    private String orderRoutingKey;


    private RabbitTemplate rabbitTemplate;



    MovimientoRepository movimientoRepository;
    CuentaRepository cuentaRepository;


    @Autowired
    public MovimientoService(MovimientoRepository movimientoRepository,CuentaRepository cuentaRepository, RabbitTemplate rabbitTemplate) {
        this.movimientoRepository=movimientoRepository;
        this.cuentaRepository=cuentaRepository;
        this.rabbitTemplate=rabbitTemplate;
    }

    public Optional<CreacionMovimientoResponseDTO> crear(CreacionMovimientoDTO creacionMovimientoDTO){

        Cuenta cuenta=cuentaRepository.findById(creacionMovimientoDTO.getIdCuenta())
                .orElseThrow((()-> new CuentaNotFoundExcdeption()));
        Movimiento movimientoDao= Movimiento.builder()
                .saldo(creacionMovimientoDTO.getSaldo())
                .valor(creacionMovimientoDTO.getValor())
                .fecha(new Date())
                .descripcion(creacionMovimientoDTO.getDescripcion())
                .tipoMovimiento(ETipoMovimiento.DEPOSITO.getDescripcion().contentEquals(creacionMovimientoDTO.getDescripcion()) ?ETipoMovimiento.DEPOSITO :ETipoMovimiento.EXTRACCION)
                .cuenta(cuenta)
                .build();
        cuenta.setSaldoDisponible(movimientoDao.getTipoMovimiento()==ETipoMovimiento.EXTRACCION
                ?cuenta.getSaldoDisponible().subtract(creacionMovimientoDTO.getValor())
                :cuenta.getSaldoDisponible().add(creacionMovimientoDTO.getValor()));

        this.movimientoRepository.save(movimientoDao);
        this.cuentaRepository.save(cuenta);
        return Optional.of(CreacionMovimientoResponseDTO.builder().status("OK").build());

    }

    public List<Movimiento> getMovimientos(Long idCliente, Date fechaDesde, Date fechaHasta){
        List<Movimiento> response= this.movimientoRepository.findMov(fechaDesde, fechaHasta);


        CuentaEvent event = new CuentaEvent();
        event.setStatus("PENDING");
        event.setMessage("Order is in pending status");
        event.setCuenta(CuentaDTO.builder()
                .idCuenta(response.get(0).getCuenta().getIdCuenta())
                        .dni(response.get(0).getCuenta().getDniCliente())
                        .numCuenta(response.get(0).getCuenta().getNumCuenta())
                        .dni(response.get(0).getCuenta().getDniCliente())
                .build());
        sendMessage(event);

        return response;

    }

    public void sendMessage(CuentaEvent orderEvent){

        // send an order event to order queue
        rabbitTemplate.convertAndSend(exchange, orderRoutingKey, orderEvent);

    }
}
