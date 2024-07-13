package com.devsu.apicuenta.service;

import com.devsu.apicuenta.configuration.CacheConfig;
import com.devsu.apicuenta.dto.*;
import com.devsu.apicuenta.exception.CuentaNotFoundExcdeption;
import com.devsu.apicuenta.model.Cuenta;
import com.devsu.apicuenta.repository.CuentaRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CuentaService {

    CuentaRepository cuentaRepository;

    public static final String EDICION_OK="Edicion OK";

    @Autowired
    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository=cuentaRepository;
    }

    public Optional<CreacionCuentaResponseDTO> crear(CreacionCuentaDTO creacionCuentaDTO){
        Random rand = new Random();

        Cuenta cuentaDao= Cuenta.builder()
                //.estado(creacionCuentaDTO.getEstado())
                .estado(creacionCuentaDTO.getEstado())
                .numCuenta(rand.nextInt(100)+1)
                .saldoInicial(creacionCuentaDTO.getSaldoInicial())
                .saldoDisponible(creacionCuentaDTO.getSaldoInicial())
                .dniCliente(creacionCuentaDTO.getDniCliente())
                .tipoCuenta(creacionCuentaDTO.getTipoCuenta())
                .build();

        this.cuentaRepository.save(cuentaDao);
        return Optional.of(CreacionCuentaResponseDTO.builder().status("OK").build());

    }

    public Optional<ResultadoResponseDTO> editar(ActualizarEditarRequestDTO cuentaEdicionDTO){

        Cuenta cuentaUpdate=this.cuentaRepository.findByNumCuenta(cuentaEdicionDTO.getNumCuenta())
                .orElseThrow(()-> new CuentaNotFoundExcdeption());

        cuentaUpdate.setNumCuenta(!Objects.isNull(cuentaEdicionDTO.getNumCuenta()) ? cuentaEdicionDTO.getNumCuenta() :null);
        cuentaUpdate.setEstado(!Objects.isNull(cuentaEdicionDTO.getEstado()) ?cuentaEdicionDTO.getEstado() :null);
        cuentaUpdate.setSaldoInicial(!Objects.isNull(cuentaEdicionDTO.getSaldoInicial()) ?cuentaEdicionDTO.getSaldoInicial() :null );
        cuentaUpdate.setTipoCuenta(!Strings.isEmpty(cuentaEdicionDTO.getTipoCuenta()) ?cuentaEdicionDTO.getTipoCuenta() :null);
        this.cuentaRepository.save(cuentaUpdate);

        return Optional.of(ResultadoResponseDTO.builder().resultado(EDICION_OK).build());
    }

    public Optional<ResultadoResponseDTO> actualizar(Map<String, String> cuentaaUpdateDTO, Integer numCuenta){
        Cuenta cuentaUpdate=this.cuentaRepository.findByNumCuenta(numCuenta)
                .orElseThrow(CuentaNotFoundExcdeption::new);

        cuentaaUpdateDTO.forEach((clave, valor)->{

            switch(clave){
                case "numCuenta" -> cuentaUpdate.setNumCuenta(Integer.valueOf(valor));
                case "estado" -> cuentaUpdate.setEstado(valor);
                case "saldoInicial" -> cuentaUpdate.setSaldoInicial(new BigDecimal(valor));
                case "tipoCuenta" -> cuentaUpdate.setTipoCuenta(valor);
            }

        });

        this.cuentaRepository.save(cuentaUpdate);

        return Optional.of(ResultadoResponseDTO.builder().resultado(EDICION_OK).build());

    }

    @Cacheable(cacheNames = CacheConfig.CUENTA_CACHE, unless = "#result == null")
    public Optional<CuentasResponseDTO> getCuentas(){

        List<Cuenta> cuentas=this.cuentaRepository.findAll();
        CuentasResponseDTO cuentasDTO=CuentasResponseDTO.builder().cuentas(new ArrayList<>()).build();
        if(!cuentas.isEmpty()){
            cuentas
                    .stream()
                    .forEach(cuenta->{
                        cuentasDTO.getCuentas().add(CuentaResponseDTO.builder()
                                .tipoCuenta(cuenta.getTipoCuenta())
                                        .numCuenta(cuenta.getNumCuenta())
                                .idCuenta(cuenta.getIdCuenta())
                                .saldoInicial(cuenta.getSaldoInicial())
                                .estado(cuenta.getEstado())
                                .build());
                    });
        }

        return Optional.of(cuentasDTO);
    }

    public Optional<ResultadoResponseDTO> borrar(Integer numCuenta){
        Cuenta cuentaUpdate=this.cuentaRepository.findByNumCuenta(numCuenta)
                .orElseThrow(CuentaNotFoundExcdeption::new);
        this.cuentaRepository.delete(cuentaUpdate);

        return Optional.of(ResultadoResponseDTO.builder().resultado(EDICION_OK).build());
    }



}
