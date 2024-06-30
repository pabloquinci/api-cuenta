package com.devsu.apicuenta.controller;

import com.devsu.apicuenta.dto.CreacionCuentaDTO;
import com.devsu.apicuenta.dto.CreacionCuentaResponseDTO;
import com.devsu.apicuenta.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/cuenta")
public class CuentaController {

    @Autowired
    CuentaService cuentaService;


    @PostMapping("/crear")
    public ResponseEntity<CreacionCuentaResponseDTO> crearCuenta(@RequestBody CreacionCuentaDTO creacionCuentaDTO) {
        Optional<CreacionCuentaResponseDTO> response= this.cuentaService.crear(creacionCuentaDTO);

        if(response.isPresent()){
            return ResponseEntity.ok(response.get());
        }

        return ResponseEntity.notFound().build();

    }
}
