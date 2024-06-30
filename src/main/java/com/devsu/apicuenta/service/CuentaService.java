package com.devsu.apicuenta.service;

import com.devsu.apicuenta.dto.CreacionCuentaDTO;
import com.devsu.apicuenta.dto.CreacionCuentaResponseDTO;
import com.devsu.apicuenta.model.Cuenta;
import com.devsu.apicuenta.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CuentaService {

    CuentaRepository cuentaRepository;


    @Autowired
    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository=cuentaRepository;
    }

    public Optional<CreacionCuentaResponseDTO> crear(CreacionCuentaDTO creacionCuentaDTO){

        Cuenta cuentaDao= Cuenta.builder()
                .estado(creacionCuentaDTO.getEstado())
                .numCuenta(creacionCuentaDTO.getNumCuenta())
                .saldoInicial(creacionCuentaDTO.getSaldoInicial())
                .estado("OK")
                .build();

        this.cuentaRepository.save(cuentaDao);
        return Optional.of(CreacionCuentaResponseDTO.builder().status("OK").build());

    }


}
