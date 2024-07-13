package com.devsu.apicuenta.service;

import com.devsu.apicuenta.configuration.CacheConfig;
import com.devsu.apicuenta.dto.*;
import com.devsu.apicuenta.exception.CuentaNotFoundExcdeption;
import com.devsu.apicuenta.exception.MovimientosException;
import com.devsu.apicuenta.exception.SinSaldoException;
import com.devsu.apicuenta.model.Cuenta;
import com.devsu.apicuenta.model.ETipoMovimiento;
import com.devsu.apicuenta.model.Movimiento;
import com.devsu.apicuenta.repository.CuentaRepository;
import com.devsu.apicuenta.repository.MovimientoRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.*;

@Service
@SessionAttributes("consultaMovimientosResponseDTO")
public class MovimientoService {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.binding.routing.key}")
    private String orderRoutingKey;

    ConsultaMovimientosResponseDTO consultaMovimientosResponseDTO= new ConsultaMovimientosResponseDTO();

    Model model;

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

        Cuenta cuenta=cuentaRepository.findByNumCuenta(creacionMovimientoDTO.getNumCuenta())
                .orElseThrow(CuentaNotFoundExcdeption::new);
        if(cuenta.getSaldoDisponible().compareTo(creacionMovimientoDTO.getValor())<0){
            throw  new SinSaldoException();
        }
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
    @Cacheable(cacheNames = CacheConfig.CUENTA_CACHE, unless = "#result == null")
    public Optional<ConsultaMovimientosResponseDTO> getMovimientos(Integer numCuenta, Date fechaDesde, Date fechaHasta,Model model){
        consultaMovimientosResponseDTO.setMovimientos(new ArrayList<>());
        List<Movimiento> response= this.movimientoRepository.findMov(numCuenta,fechaDesde, fechaHasta)
                .orElseThrow(MovimientosException::new);

        if(response.isEmpty()){
            throw new MovimientosException();
        }

        CuentaEvent event = new CuentaEvent();
        event.setStatus("PENDING");
        event.setMessage("Solicitando info usuario");
        response.stream().forEach(m->{
            consultaMovimientosResponseDTO.getMovimientos().add(MovimientoDTO.
                    builder()
                            .descripcion(m.getDescripcion())
                            .tipoCuenta(m.getCuenta().getTipoCuenta())
                            .valorMovimiento(m.getValor())
                            .estado(m.getCuenta().getEstado())
                            .fecha(m.getFecha())
                            .tipoCuenta(m.getCuenta().getTipoCuenta())
                            .saldoInicial(m.getCuenta().getSaldoInicial())
                            .numCuenta(m.getCuenta().getNumCuenta())
                            .tipoCuenta(m.getCuenta().getTipoCuenta())
                    .build());

        });
        event.setCuenta(CuentaDTO.builder()
                .idCuenta(response.get(0).getCuenta().getIdCuenta())
                        .dni(response.get(0).getCuenta().getDniCliente())
                        .numCuenta(response.get(0).getCuenta().getNumCuenta())
                .build());
        sendMessage(event);
        model.addAttribute(consultaMovimientosResponseDTO);

        return Optional.of(consultaMovimientosResponseDTO);

    }

    //Este bean se define para inyectar los movimientos en el consumer
    @Bean
    public ConsultaMovimientosResponseDTO consultaMovimientosResponseDTO(){
        return consultaMovimientosResponseDTO;
    }


    public void sendMessage(CuentaEvent orderEvent){
        rabbitTemplate.convertAndSend(exchange, orderRoutingKey, orderEvent);

    }
}
